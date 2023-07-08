package com.geekydroid.savestmentbackend.domain.users;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserSignInRequest {

    private String userName;
    private String userFullName;
    private String userDisplayName;
    private String userEmailAddress;
    private String userProfileUrl;

}
