package com.my.library_base.model

class EventBusMessageEvent(var code: Int, var message: String) {
    override fun toString(): String {
        return "EventBusMessageEvent{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}'
    }
}