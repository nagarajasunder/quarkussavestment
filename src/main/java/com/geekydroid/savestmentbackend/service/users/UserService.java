package com.geekydroid.savestmentbackend.service.users;

import com.geekydroid.savestmentbackend.domain.users.UserSignInRequest;
import com.geekydroid.savestmentbackend.utils.models.NetworkResponse;

public interface UserService {

    NetworkResponse authenticateUser(UserSignInRequest userSignInRequest);
    NetworkResponse createNewUser(UserSignInRequest userSignInRequest);

    Boolean verifyJwtToken(String userId,String userReferenceId,String jwtToken);

}
