package com.amadeus.nutrasoft.calc;

import com.amadeus.nutrasoft.model.*;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.amadeus.nutrasoft.calc.Macronutrient.*;
import static com.amadeus.nutrasoft.commons.Utils.round;
import static com.amadeus.nutrasoft.commons.Utils.valueOfPct;
import static com.amadeus.nutrasoft.constants.Constants.FOOD_CLASS_GRAIN;
import static com.amadeus.nutrasoft.constants.Constants.PART_CODE_GARNISH;

public class DietSolver {
    public static void solve(PlanDay planDay, Preference preference, Dish dish) {
        List<DishPart> dishPartList = dish.getDishPartList();
        int nutrientCount = 3;

        // Parámetros de la optimización.
        double objCoefficients[] = new double[dishPartList.size()];
        double conCoefficients[][] = new double[nutrientCount][dishPartList.size()];
        double conRightSides[] = new double[nutrientCount];

        /*
         * Establece los coeficientes de la función objetivo y de las restricciones.
         */

        // Itera la lista de componentes o partes: proteínas, guarnición y salsa.
        for (DishPart dishPart : dishPartList) {
            float cost = 0;
            float pro = 0;
            float cho = 0;
            float fat = 0;

            float foodCount = dishPart.getDishPartFoodList().size();
            float grainCount = getFoodClassCount(dishPart.getDishPartFoodList(), FOOD_CLASS_GRAIN);

            // Itera la lista de alimentos de cada componente.
            for (DishPartFood dishPartFood : dishPart.getDishPartFoodList()) {
                Food food = dishPartFood.getFood();

                // Inicio: Asigna el porcentaje del peso del alimento con
                // respecto al peso total de los alimentos en el componente.
                float weightPct = 100 / foodCount;

                // Establece la proporción de la guarnición de almidón y la
                // guarnición de vegetales.
                byte grainPct = 60;
                switch (dishPart.getPartCode()) {
                    case PART_CODE_GARNISH:
                        // Si además de granos existen vegetales u otros alimentos.
                        if (grainCount < foodCount) {
                            if (food.getFoodGroup().getFoodClass().equals(FOOD_CLASS_GRAIN)) {
                                // Establece la proporción de la guarnición de almidón.
                                weightPct = grainPct / grainCount;
                            } else {
                                // Establece la proporción de la guarnición de vegetales u otros alimentos.
                                weightPct = (100 - grainPct) / (foodCount - grainCount);
                            }
                        }

                        break;
                }

                dishPartFood.setWeightPct(weightPct);
                // Fin: Asigna el porcentaje del peso del alimento.

                // Coeficientes de la función objetivo.
                cost += valueOfPct(weightPct, food.getCost());

                // Calcula el aporte de nutrientes del alimento según el
                // porcentaje del mismo en el componente y lo totaliza.
                pro += valueOfPct(weightPct, getValuePer100Gram(food, Nutrient.PROCNT));
                cho += valueOfPct(weightPct, getValuePer100Gram(food, Nutrient.CHOCDF));
                fat += valueOfPct(weightPct, getValuePer100Gram(food, Nutrient.FAT));
            }

            int i = dishPartList.indexOf(dishPart);

            // Coeficientes de la función objetivo.
            objCoefficients[i] = cost;

            // Coeficientes de las restricciones.
            // Suma de los aporte de nutrientes del componente.
            conCoefficients[0][i] = pro;
            conCoefficients[1][i] = cho;
            conCoefficients[2][i] = fat;
        }

        /*
         * Establece las restricciones del lado derecho.
         */

        byte mealCount = preference.getMealCount();
        // Requerimiento de nutrientes que debe ser cubierto en la comida.
        conRightSides[0] = planDay.getPro() / mealCount;
        conRightSides[1] = planDay.getCho() / mealCount;
        conRightSides[2] = planDay.getFat() / mealCount;

        /*
         * Realiza la optimización.
         */

        ObjectHolder<Double> errorHolder = new ObjectHolder<>(0.1);
        PointValuePair solution = optimize(objCoefficients, conCoefficients, conRightSides, errorHolder);
        dish.setError(round(errorHolder.param, 2));

        /*
         * Asigna los pesos de los alimentos.
         */

        double value = solution.getValue() * 100;
        dish.setCost((float) value);

        float totalPro = 0.0F;
        float totalCho = 0.0F;
        float totalFat = 0.0F;
        double totalEnerg = 0;

        for (DishPart dishPart : dishPartList) {
            int i = dishPartList.indexOf(dishPart);
            float result = (float) solution.getPoint()[i];

            // Itera la lista de alimentos de cada componente.
            for (DishPartFood dishPartFood : dishPart.getDishPartFoodList()) {
                // Asigna proporcionalmente cada peso a cada alimento.
                float weight = result * dishPartFood.getWeightPct();
                dishPartFood.setWeight(round(weight, 2));

                // Calcula el aporte nutricional del alimento según el peso encontrado.
                Food food = dishPartFood.getFood();
                float pro = getValuePerGram(food, Nutrient.PROCNT) * weight;
                float cho = getValuePerGram(food, Nutrient.CHOCDF) * weight;
                float fat = getValuePerGram(food, Nutrient.FAT) * weight;
                double energ = PRO.energ(pro) + CHO.energ(cho) + FAT.energ(fat);

                // Asigna el aporte nutricional.
                dishPartFood.setPro(round(pro, 1));
                dishPartFood.setCho(round(cho, 1));
                dishPartFood.setFat(round(fat, 1));
                dishPartFood.setEnerg((short) round(energ, 0));

                // Calcula el aporte nutricional de la comida.
                totalPro += pro;
                totalCho += cho;
                totalFat += fat;
                totalEnerg += energ;
            }
        }

        // Asigna el aporte nutricional de la comida.
        dish.setTotalPro(round(totalPro, 1));
        dish.setTotalCho(round(totalCho, 1));
        dish.setTotalFat(round(totalFat, 1));
        dish.setTotalEnerg((short) round(totalEnerg, 0));

        /*
         * Se unifican los alimentos en un solo array. Es necesario hacer esto para que se muestre
         * correctamente las lineas de los items en la UI. Ademas simplica el objeto que es retornado.
         */

        List<DishPartFood> dishPartFoodList = new ArrayList<>();

        for (DishPart dishPart : dishPartList) {
            dishPartFoodList.addAll(dishPart.getDishPartFoodList());
        }

        dish.setDishPartFoodList(dishPartFoodList);

        // Limpia los datos que ya no seran usados.
        dish.setDishPartList(null);
    }

    private static float getFoodClassCount(List<DishPartFood> dishPartFoodList, String foodClass) {
        float count = 0;

        for (DishPartFood dishPartFood : dishPartFoodList) {
            if (dishPartFood.getFood().getFoodGroup().getFoodClass().equals(foodClass)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Obtiene el aporte del nutriente en 100 g del alimento.
     *
     * @param food
     * @param tagName
     * @return
     */
    private static float getValuePer100Gram(Food food, String tagName) {
        for (FoodNutrient foodNutrient : food.getFoodNutrientList()) {
            if (foodNutrient.getNutrient().getTagName().equals(tagName)) {
                return foodNutrient.getValue();
            }
        }

        return 0f;
    }

    private static float getValuePerGram(Food food, String tagName) {
        return getValuePer100Gram(food, tagName) / 100;
    }

    /**
     * Calcula los pesos de los alimentos de una comida. La función calcula la cantidad de cada
     * alimento en la comida, de manera que se cubran los requerimientos de cada macronutriente
     * y que el costo total sea el mínimo posible.
     *
     * @param objCoefficients Precios de los alimentos.
     * @param conCoefficients Peso de los macronutrientes por cada 100 gramos de alimento,
     * @param conRightSides   Requerimiento de macronutrientes en gramos.
     * @param errorHolder     Un margen de error de +/- 10% es aceptable. En las llamadas recursivas este valor se incrementa.
     * @return El objeto que contine los pesos de los alimentos y el valor óptimo de la función.
     */
    private static PointValuePair optimize(final double objCoefficients[], final double conCoefficients[][], final double conRightSides[], final
    ObjectHolder<Double> errorHolder) {
        double error = errorHolder.param;

        // Configura la función objetivo. Asigna los precios de los alimentos.
        LinearObjectiveFunction objectiveFunction = new LinearObjectiveFunction(objCoefficients, 0);

        // Configura las restricciones. Establece los requerimientos de macronutrientes.
        Collection<LinearConstraint> constraints = new ArrayList<>();

        // Iteración por cada nutriente.
        for (int i = 0; i < conCoefficients.length; i++) {
            // Por ejemplo, en una iteracion conCoefficients[i] es un arreglo con la proteína que
            // aporta cada alimento (elemento del arreglo) y conRightSides[i] es el requerimiento
            // de proteína en la comida.
            constraints.add(new LinearConstraint(conCoefficients[i], Relationship.GEQ, conRightSides[i] * (1 - error)));
            constraints.add(new LinearConstraint(conCoefficients[i], Relationship.LEQ, conRightSides[i] * (1 + error)));
        }

        try {
            // Realiza la optimización. Obtiene los pesos de los alimentos para cubrir los requerimientos
            // de macronutrientes y minimiza el costo.
            return new SimplexSolver().optimize(
                    objectiveFunction,
                    new LinearConstraintSet(constraints),
                    GoalType.MINIMIZE,
                    new NonNegativeConstraint(true)
            );
        } catch (NoFeasibleSolutionException e) {
            // Se llama de forma recursiva aumentando el margen de error hasta que la solución sea factible.
            errorHolder.param = error + 0.05;
            return optimize(objCoefficients, conCoefficients, conRightSides, errorHolder);
        }
    }

    public static class ObjectHolder<T> {
        T param;

        ObjectHolder(T param) {
            this.param = param;
        }
    }
}
