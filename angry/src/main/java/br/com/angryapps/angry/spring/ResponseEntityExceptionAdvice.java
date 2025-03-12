package br.com.angryapps.angry.spring;

import br.com.angryapps.angry.api.exceptions.BadRequestResponseException;
import br.com.angryapps.angry.api.exceptions.InternalServerErrorResponseException;
import br.com.angryapps.angry.api.exceptions.NotFoundResponseException;
import br.com.angryapps.angry.api.exceptions.UnauthorizedResponseException;
import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handleUnauthorizedException(UnauthorizedResponseException ex) {
        return ex.getResponse();
    }

    @ResponseBody
    @ExceptionHandler(InternalServerErrorResponseException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse handleInternalServerErrorException(InternalServerErrorResponseException ex) {
        return ex.getResponse();
    }

    // Handling exceptions for validation constraints
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleNotValidConstrainsException(MethodArgumentNotValidException ex) {
        List<String> errors = Arrays.stream(ex.getDetailMessageArguments())
                                    .map(String::valueOf)
                                    .filter(d -> !d.isEmpty())
                                    .toList();

        return ApiResponses.error(String.join(", ", errors));
    }
}
