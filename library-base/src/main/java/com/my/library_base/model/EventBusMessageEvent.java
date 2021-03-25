package com.my.library_base.model;

public class EventBusMessageEvent {
    private int code;
    private String message;

    public EventBusMessageEvent(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "EventBusMessageEvent{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
