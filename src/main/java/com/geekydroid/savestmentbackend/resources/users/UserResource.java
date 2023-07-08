package com.geekydroid.savestmentbackend.resources.users;

import com.geekydroid.savestmentbackend.domain.users.UserSignInRequest;
import com.geekydroid.savestmentbackend.service.users.UserService;
import com.geekydroid.savestmentbackend.utils.ResponseUtil;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    @POST()
    @Path("/userauth")
    public Response userAuth(UserSignInRequest userSignInRequest) {
        if (userSignInRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("authentication request is empty")
                    .build();
        }
        if (userSignInRequest.getUserEmailAddress() == null || userSignInRequest.getUserEmailAddress().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User email address is empty")
                    .build();
        }

        return ResponseUtil.getResponseFromResult(userService.authenticateUser(userSignInRequest));
    }

}
