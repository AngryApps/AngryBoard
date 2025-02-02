package br.com.angryapps.angry.spring;

import br.com.angryapps.angry.api.exceptions.BadRequestResponseException;
import br.com.angryapps.angry.api.exceptions.NotFoundResponseException;
import br.com.angryapps.angry.api.exceptions.UnauthorizedResponseException;
import br.com.angryapps.angry.api.responses.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResponseEntityExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BadRequestResponseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleBadRequestException(BadRequestResponseException ex) {
        return ex.getResponse();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundResponseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse handleNotFoundException(NotFoundResponseException ex) {
        return ex.getResponse();
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedResponseException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse handleUnauthorizedException(UnauthorizedResponseException ex) {
        return ex.getResponse();
    }
}
