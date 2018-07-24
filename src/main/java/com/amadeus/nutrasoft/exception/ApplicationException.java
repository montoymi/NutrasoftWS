package com.amadeus.nutrasoft.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/*
 * Esta clase permite enviar un exception producto de un error logico. De esta forma se evita que se propague el
 * 500 Internal Server Error.
 * Se envia el código 422 el cual indica que han producion un errores semánticos (errores lógicos).
 *
 * Nota: Para que progague esta exception es necesario registrar el paquete que contiene esta clase en el web.xml
 * <init-param>
 *   <param-name>jersey.config.server.provider.packages</param-name>
 *   <param-value>com.amadeus.nutrasoft.rest;com.amadeus.nutrasoft.exception</param-value>
 * </init-param>
 */
@Provider
public class ApplicationException extends RuntimeException implements ExceptionMapper<ApplicationException> {
    public static final String DIET_PLAN_NOT_FOUND = "diet/plan-not-found";
    public static final String DIET_MENU_NOT_FOUND = "diet/menu-not-found";
    public static final String MEAS_PLAN_NOT_FOUND = "meas/plan-not-found";

    // Este constructor es necesario, si no está ocurre una exception.
    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(ApplicationException exception) {
        // Unprocessable Entity. La solicitud está bien formada pero fue imposible seguirla debido a errores
        // semánticos.
        int status = 422;

        return Response.status(status).entity(exception.getMessage()).type("text/plain").build();
    }
}
