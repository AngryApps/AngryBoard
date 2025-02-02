package br.com.angryapps.angry.api.exceptions;

import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.BaseResponse;

public final class BadRequestResponseException extends ResponseException {

    public BadRequestResponseException(String message) {
        super(ApiResponses.error(message));
    }

    public BadRequestResponseException(BaseResponse response) {
        super(response);
    }
}
