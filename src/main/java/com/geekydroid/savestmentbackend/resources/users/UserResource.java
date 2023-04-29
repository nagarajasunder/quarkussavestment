package com.geekydroid.savestmentbackend.resources.users;

import com.geekydroid.savestmentbackend.domain.users.UserSignInRequest;
import com.geekydroid.savestmentbackend.service.users.UserService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserResource {


    @Inject
    UserService userService;

    @POST()
    @Path("/signin")
    public Response userSignIn(UserSignInRequest userSignInRequest) {

        if (userSignInRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sign-in request is empty")
                    .build();
        }
        if (userSignInRequest.getUserEmailAddress() == null || userSignInRequest.getUserEmailAddress().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User email address is empty")
                    .build();
        }

        return ResponseUtil.getResponseFromResult(userService.createNewUser(userSignInRequest));

    }

}
