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
    public static final String PLAN_NOT_FOUND = "plan-not-found";
    public static final String MENU_NOT_FOUND = "menu-not-found";
    public static final String PLAN_CAN_NOT_DELETE = "plan-can-not-delete";
    public static final String INACTIVE_USER = "inactive-user";
    public static final String NO_APPROVED_COACH = "no-approved-coach";

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
