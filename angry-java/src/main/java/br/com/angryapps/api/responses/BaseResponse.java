package br.com.angryapps.api.responses;

import java.util.Map;

public class BaseResponse {

    private final boolean success;
    private final String message;
    private final Map<String, Object> metadata;

    public BaseResponse(boolean success, String message, Map<String, Object> metadata) {
        this.success = success;
        this.message = message;
        this.metadata = metadata;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public boolean isSuccess() {
        return success;
    }
}
