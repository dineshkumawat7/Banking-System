package com.bank.account.service.entity;

import com.bank.account.service.enums.AccountType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "accounts")
public class Account implements Serializable {
    @Id
    private String id;
    private String userId;
    private String accountNumber;
    private String accountHolderName;
    private AccountType accountType;
    private Double amount;
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
