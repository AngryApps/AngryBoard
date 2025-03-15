package br.com.angryapps.api.responses;

import java.util.Map;

public class SingleDataResponse<T> extends BaseResponse {

    private final T data;

    public SingleDataResponse(String message, Map<String, Object> metadata, T data) {
        super(true, message, metadata);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
