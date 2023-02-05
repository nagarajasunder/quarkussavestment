package com.geekydroid.savestmentbackend.utils.models;

public class GenericNetworkResponse {

    private int statusCode;
    private String status;

    private String message;

    private Object body;

    public GenericNetworkResponse() {
    }

    public GenericNetworkResponse(int statusCode, String status, String message, Object body) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
