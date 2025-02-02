package br.com.angryapps.angry.api.responses;

import java.util.List;
import java.util.Map;

public final class ApiResponses {

    public static BaseResponse ok() {
        return new BaseResponse(true, null, null);
    }

    public static BaseResponse ok(String message) {
        return new BaseResponse(true, message, null);
    }

    public static BaseResponse ok(String message, Map<String, Object> metadata) {
        return new BaseResponse(true, message, metadata);
    }

    public static BaseResponse error() {
        return new BaseResponse(false, null, null);
    }

    public static BaseResponse error(String message) {
        return new BaseResponse(false, message, null);
    }

    public static BaseResponse error(String message, Map<String, Object> metadata) {
        return new BaseResponse(false, message, metadata);
    }

    public static <T> SingleDataResponse<T> single(T data) {
        return new SingleDataResponse<>(null, null, data);
    }

    public static <T> SingleDataResponse<T> single(String message, T data) {
        return new SingleDataResponse<>(message, null, data);
    }

    public static <T> SingleDataResponse<T> single(String message, T data, Map<String, Object> metadata) {
        return new SingleDataResponse<>(message, metadata, data);
    }

    public static <T> ListDataResponse<T> list(List<T> data) {
        return new ListDataResponse<>(null, null, data);
    }

    public static <T> ListDataResponse<T> list(String message, List<T> data) {
        return new ListDataResponse<>(message, null, data);
    }

    public static <T> ListDataResponse<T> list(String message, List<T> data, Map<String, Object> metadata) {
        return new ListDataResponse<>(message, metadata, data);
    }
}
