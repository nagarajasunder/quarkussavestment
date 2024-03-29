package com.geekydroid.savestmentbackend.repository.users;

import com.geekydroid.savestmentbackend.domain.users.User;

import java.util.Date;

public interface UserRepository {

    String createJwtToken(String userId, String userUuid,Date issuedAt, Date expiredAt);

    Boolean verifyJwtToken(String userId,String userReferenceId,String jwtToken);

    User createNewUser(User newUser);

    Boolean checkIfExistingUser(String userEmail);

    User findUserByEmail(String userEmailAddress);
}
