package com.geekydroid.savestmentbackend.service.users;

import com.geekydroid.savestmentbackend.domain.users.User;
import com.geekydroid.savestmentbackend.domain.users.UserSignInRequest;
import com.geekydroid.savestmentbackend.domain.users.UserSignInResponse;
import com.geekydroid.savestmentbackend.repository.users.UserRepository;
import com.geekydroid.savestmentbackend.utils.models.Error;
import com.geekydroid.savestmentbackend.utils.models.Exception;
import com.geekydroid.savestmentbackend.utils.models.GenericNetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;
import com.geekydroid.savestmentbackend.utils.models.Success;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public NetworkResponse authenticateUser(UserSignInRequest userSignInRequest) {
        User existingUser = userRepository.findUserByEmail(userSignInRequest.getUserEmailAddress());
    if (existingUser != null) {
        return generateAccessToken(existingUser);
    }
    return new Exception(Response.Status.BAD_REQUEST,null,"User doesn't exist");
    }

    @Override
    public NetworkResponse createNewUser(UserSignInRequest userSignInRequest) {
        if (!userRepository.checkIfExistingUser(userSignInRequest.getUserEmailAddress())) {
            LocalDateTime now = LocalDateTime.now();
            User newUser = new User(
                    UUID.randomUUID(),
                    userSignInRequest.getUserFullName(),
                    userSignInRequest.getUserDisplayName(),
                    userSignInRequest.getUserEmailAddress(),
                    userSignInRequest.getUserProfileUrl(),
                    now,
                    now
            );
            newUser = userRepository.createNewUser(newUser);
            return generateAccessToken(newUser);
        } else {
            User existingUser = userRepository.findUserByEmail(userSignInRequest.getUserEmailAddress());
            if (existingUser != null) {
                return generateAccessToken(existingUser);
            } else {
                return new Error(Response.Status.INTERNAL_SERVER_ERROR, null, new GenericNetworkResponse(
                        Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                        "FAILED",
                        "Cannot able to find user",
                        null
                ));
            }
        }

    }

    @Override
    public Boolean verifyJwtToken(String userId, String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty() || userId == null || userId.isEmpty()) {
            return false;
        }
        return userRepository.verifyJwtToken(userId, jwtToken);
    }

    private Success generateAccessToken(User existingUser) {
        long expiryTime = 24*60*60*1000L;
        String accessToken = userRepository.createJwtToken(existingUser.getUserId(), new Date(), new Date(System.currentTimeMillis() + expiryTime));
        return new Success(
                Response.Status.CREATED,
                null,
                new UserSignInResponse(existingUser.getUserId(), existingUser.getUserEmailAddress(),accessToken)
        );
    }
}
