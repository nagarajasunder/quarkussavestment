package com.geekydroid.domain.users;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "SAVESTMENT_USERS")
public class User {


    @Id
    @Column(
            name = "user_uid",
            unique = true
    )
    private UUID userId;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "user_display_name")
    private String userDisplayName;

    @Column(name = "user_email_address")
    private String userEmailAddress;

    @Column(name = "user_profile_url")
    private String userProfileUrl;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    public User(
            UUID userId,
            String userFullName,
            String userDisplayName,
            String userEmailAddress,
            String userProfileUrl,
            LocalDateTime createdOn,
            LocalDateTime updatedOn
    ) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userDisplayName = userDisplayName;
        this.userEmailAddress = userEmailAddress;
        this.userProfileUrl = userProfileUrl;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public User() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
