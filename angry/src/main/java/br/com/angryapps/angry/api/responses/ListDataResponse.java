package br.com.angryapps.angry.api.responses;

import java.util.List;
import java.util.Map;

public class ListDataResponse<T> extends BaseResponse {

    private final List<T> data;

    public ListDataResponse(String message, Map<String, Object> metadata, List<T> data) {
        super(true, message, metadata);
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }
}
