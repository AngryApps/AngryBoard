package br.com.angryapps.angry.api.exceptions;

import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.BaseResponse;

public final class InternalServerErrorResponseException extends ResponseException {

    public InternalServerErrorResponseException(String message) {
        super(ApiResponses.error(message));
    }

    public InternalServerErrorResponseException(BaseResponse response) {
        super(response);
    }
}
