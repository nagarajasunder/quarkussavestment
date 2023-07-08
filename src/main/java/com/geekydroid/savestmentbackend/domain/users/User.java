package com.geekydroid.savestmentbackend.domain.users;

import com.geekydroid.savestmentbackend.db.ExpenditureNumberSequenceGenerator;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "SAVESTMENT_USERS")
@Getter
@Setter
public class User extends PanacheEntityBase {


    @Column(
            name = "user_uid",
            unique = true
    )
    private UUID userUuid;



    @GeneratedValue(
            generator = "user_id_generator"
    )
    @GenericGenerator(
            name = "user_id_generator",
            strategy = "com.geekydroid.savestmentbackend.db.ExpenditureNumberSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = ExpenditureNumberSequenceGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = ExpenditureNumberSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "SVMT_"),
                    @org.hibernate.annotations.Parameter(name = ExpenditureNumberSequenceGenerator.NUMBER_FORMAT_PARAMETER, value = "%d"),
            }
    )
    @Column(name = "user_id")
    @Id
    private String userId;

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

    public User() {
    }

    public User(
            UUID userUuid,
            String userFullName,
            String userDisplayName,
            String userEmailAddress,
            String userProfileUrl,
            LocalDateTime createdOn,
            LocalDateTime updatedOn
    ) {
        this.userUuid = userUuid;
        this.userFullName = userFullName;
        this.userDisplayName = userDisplayName;
        this.userEmailAddress = userEmailAddress;
        this.userProfileUrl = userProfileUrl;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }
}
