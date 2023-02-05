package com.geekydroid.savestmentbackend.utils.models;

import javax.ws.rs.core.Response;

public abstract sealed class NetworkResponse permits Success, Error, Exception {

    public Response.Status statusCode;

    public java.lang.Exception exception;

    public Object body;

    NetworkResponse(Response.Status code, java.lang.Exception exception, Object body) {
        this.statusCode = code;
        this.exception = exception;
        this.body = body;
    }
}
