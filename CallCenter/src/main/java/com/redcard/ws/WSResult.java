package com.redcard.ws;

public class WSResult<T> {

    private Boolean isError;

    private Integer errorCode;

    private String message;

    private T content;

    public WSResult() {
        this.isError = false;
    }

    public WSResult(String message) {
        this();
        this.message = message;
    }

    public WSResult(Boolean isError, Integer errorCode) {
        super();
        this.isError = isError;
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String message) {
        this.isError = true;
        this.message = message;
    }

    public Boolean getIsError() {
        return isError;
    }

    public void setIsError(Boolean isError) {
        this.isError = isError;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
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
