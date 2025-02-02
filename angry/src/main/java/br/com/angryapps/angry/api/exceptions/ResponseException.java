package br.com.angryapps.angry.api.exceptions;

import br.com.angryapps.angry.api.responses.BaseResponse;

public abstract class ResponseException extends RuntimeException {

    private BaseResponse response;

    public ResponseException(BaseResponse response) {
        this.response = response;
    }

    public BaseResponse getResponse() {
        return response;
    }
}
