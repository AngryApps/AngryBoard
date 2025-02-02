package br.com.angryapps.angry.api.exceptions;

import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.BaseResponse;

public final class UnauthorizedResponseException extends ResponseException {

    public UnauthorizedResponseException(String message) {
        super(ApiResponses.error(message));
    }

    public UnauthorizedResponseException(BaseResponse response) {
        super(response);
    }
}
