package com.geekydroid.savestmentbackend.utils;

import com.geekydroid.savestmentbackend.utils.models.Exception;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import javax.ws.rs.core.Response;

public class ResponseUtil {


    public static Response getResponseFromResult(NetworkResponse result) {

        if (result instanceof Exception && result.exception != null) {
            java.lang.Exception exception = result.exception;
            GenericNetworkResponse message = new GenericNetworkResponse();
            message.setStatus("FAILED");
            message.setBody(result.body);
            if (result.statusCode != null) {
                message.setStatusCode(result.statusCode.getStatusCode());
            }
            if (exception.getMessage() != null) {
                message.setMessage(exception.getMessage());
            }
            return Response.status(result.statusCode)
                    .entity(message)
                    .build();
        }


        return Response.status(result.statusCode)
                .entity(result.body)
                .build();
    }

}

