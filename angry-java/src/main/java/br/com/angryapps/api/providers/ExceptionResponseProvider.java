package br.com.angryapps.api.providers;

import br.com.angryapps.api.exceptions.BadRequestResponseException;
import br.com.angryapps.api.exceptions.NotFoundResponseException;
import br.com.angryapps.api.exceptions.UnauthorizedResponseException;
import br.com.angryapps.api.responses.ApiResponses;
import br.com.angryapps.api.responses.BaseResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.stream.Collectors;

public class ExceptionResponseProvider {

    @Provider
    public static class NotFoundExceptionMapper implements ExceptionMapper<NotFoundResponseException> {
        @Override
        public Response toResponse(NotFoundResponseException exception) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(exception.getResponse())
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }
    }

    @Provider
    public static class BadRequestExceptionMapper implements ExceptionMapper<BadRequestResponseException> {
        @Override
        public Response toResponse(BadRequestResponseException exception) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(exception.getResponse())
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }
    }

    @Provider
    public static class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedResponseException> {
        @Override
        public Response toResponse(UnauthorizedResponseException exception) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity(exception.getResponse())
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }
    }

    @Provider
    public static class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
        @Override
        public Response toResponse(ConstraintViolationException exception) {
            String message = exception.getConstraintViolations().stream()
                                      .map(ConstraintViolation::getMessage)
                                      .collect(Collectors.joining(", "));

            BaseResponse response = ApiResponses.error(message);

            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(response)
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }
    }

    @Provider
    public static class GenericExceptionMapper implements ExceptionMapper<Throwable> {
        @Override
        public Response toResponse(Throwable exception) {
            BaseResponse response = ApiResponses.error("Internal server error: " + exception.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(response)
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }
    }
}
