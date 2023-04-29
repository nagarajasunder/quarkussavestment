package com.geekydroid.savestmentbackend.repository.users;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.geekydroid.savestmentbackend.domain.users.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

@ApplicationScoped
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Inject
    EntityManager entityManager;

    Algorithm algorithm = Algorithm.HMAC256("savestment");
    JWTVerifier verifier = JWT.require(algorithm).build();

    @Override
    public String createJwtToken(String userId, Date issuedAt, Date expiredAt) {

        return JWT.create()
                .withIssuer("Savestment")
                .withClaim("user_id",userId)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiredAt)
                .sign(algorithm);
    }

    @Override
    public Boolean verifyJwtToken(String userId,String jwtToken) {
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            String tokenUserId = decodedJWT.getClaim("user_id").asString();
            return ((Objects.equals(userId, tokenUserId)) && decodedJWT.getExpiresAt().after(new Date(System.currentTimeMillis())));
        }
        catch (JWTVerificationException e) {
            return false;
        }
    }

    @Override
    public User createNewUser(User newUser) {
        entityManager.persist(newUser);
        return newUser;
    }

    @Override
    public Boolean checkIfExistingUser(String userEmail) {
        User ExistingUser = User.find("userEmailAddress",userEmail).firstResult();
        return ExistingUser != null;
    }

    @Override
    public User findUserByEmail(String userEmailAddress) {
        return User.find("userEmailAddress",userEmailAddress).firstResult();
    }
}
