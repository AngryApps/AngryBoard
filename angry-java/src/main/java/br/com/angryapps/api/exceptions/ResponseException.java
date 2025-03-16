package br.com.angryapps.api.exceptions;

import br.com.angryapps.api.responses.BaseResponse;

public abstract class ResponseException extends RuntimeException {

    private BaseResponse response;

    public ResponseException(BaseResponse response) {
        this.response = response;
    }

    public BaseResponse getResponse() {
        return response;
    }
}
