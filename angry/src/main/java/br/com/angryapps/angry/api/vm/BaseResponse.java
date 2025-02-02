package br.com.angryapps.angry.api.vm;

public class BaseResponse<T> {

    private Boolean success;
    private T content;
    private String message;

    public BaseResponse() {
    }

    public BaseResponse(Boolean success) {
        this.success = success;
    }

    public BaseResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BaseResponse(Boolean success, T content) {
        this.success = success;
        this.content = content;
    }

    public BaseResponse(Boolean success, T content, String message) {
        this.success = success;
        this.content = content;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
