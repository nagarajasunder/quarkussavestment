package com.geekydroid.savestmentbackend.utils.models;

import javax.ws.rs.core.Response;

public final class Success extends NetworkResponse {

    public Response.Status statusCode;

    public java.lang.Exception exception;

    public Object body;

    public Success(Response.Status code, java.lang.Exception exception, Object body) {
        super(code, exception, body);
        this.statusCode = code;
        this.exception = exception;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Success{" +
                "statusCode=" + statusCode +
                ", exception=" + exception +
                ", body=" + body +
                '}';
    }
}
