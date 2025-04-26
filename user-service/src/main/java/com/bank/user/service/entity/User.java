package com.bank.user.service.entity;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User implements Serializable {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobNumber;
    private String password;
    @CreatedDate
    private String createdAt;
    @LastModifiedDate
    private String updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updateBy;
    private boolean isActive;
}
