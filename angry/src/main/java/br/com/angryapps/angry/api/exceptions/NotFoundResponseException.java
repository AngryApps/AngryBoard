package br.com.angryapps.angry.api.exceptions;

import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.BaseResponse;

public final class NotFoundResponseException extends ResponseException {

    public NotFoundResponseException(String message) {
        super(ApiResponses.error(message));
    }

    public NotFoundResponseException(BaseResponse response) {
        super(response);
    }
}
