package com.geekydroid.savestmentbackend.interceptors;

import com.geekydroid.savestmentbackend.service.users.UserService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;
import com.geekydroid.savestmentbackend.utils.models.Exception;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class AuthenticationInterceptor implements ContainerRequestFilter {

    @Context
    UriInfo info;

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        final String path = info.getPath();


        if (!path.contains("signin")) {
            String accessToken = containerRequestContext.getHeaderString("Authorization");
            String userID = containerRequestContext.getHeaderString("user_id");
            if (userID == null || userID.isEmpty() || accessToken == null || !accessToken.startsWith("Bearer ")) {
                abortRequest(containerRequestContext);
                return;
            }
            accessToken = accessToken.substring(7);
            if (!userService.verifyJwtToken(userID,accessToken)) {
                abortRequest(containerRequestContext);
            }
        }

    }

    private static void abortRequest(ContainerRequestContext containerRequestContext) {
        NetworkResponse response = new Exception(Response.Status.UNAUTHORIZED,null, new GenericNetworkResponse(Response.Status.UNAUTHORIZED.getStatusCode(),"FAILED","Invalid Token",null));
        containerRequestContext.abortWith(ResponseUtil.getResponseFromResult(response));
    }
}
