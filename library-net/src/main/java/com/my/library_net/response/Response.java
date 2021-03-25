package com.my.library_net.response;

public class Response<T> {
    private T body;
    private String message;
    private String error;
    private String status;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "body=" + body +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
