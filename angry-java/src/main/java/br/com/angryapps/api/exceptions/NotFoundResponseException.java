package br.com.angryapps.api.exceptions;

import br.com.angryapps.api.responses.ApiResponses;
import br.com.angryapps.api.responses.BaseResponse;

public final class NotFoundResponseException extends ResponseException {

    public NotFoundResponseException(String message) {
        super(ApiResponses.error(message));
    }

    public NotFoundResponseException(BaseResponse response) {
        super(response);
    }
}
