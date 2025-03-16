package br.com.angryapps.api.exceptions;

import br.com.angryapps.api.responses.ApiResponses;
import br.com.angryapps.api.responses.BaseResponse;

public final class UnauthorizedResponseException extends ResponseException {

    public UnauthorizedResponseException(String message) {
        super(ApiResponses.error(message));
    }

    public UnauthorizedResponseException(BaseResponse response) {
        super(response);
    }
}
