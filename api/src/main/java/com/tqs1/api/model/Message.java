package com.tqs1.api.model;


import org.springframework.stereotype.Component;


@Component
public class Message<T> {

    private T data;
    private String detail;
    private boolean success;

    public Message() {}

    public Message(T data, String detail, boolean success) {

        this.data = data;
        this.detail = detail;
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isSuccess() {
        return success;
    }
}
