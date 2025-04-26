package com.bank.user.service.controller;

import com.bank.user.service.entity.User;
import com.bank.user.service.exception.UserServiceException;
import com.bank.user.service.model.UpdateUserRequest;
import com.bank.user.service.model.UserRegistrationRequest;
import com.bank.user.service.model.common.CommonResponse;
import com.bank.user.service.service.UserService;
import com.bank.user.service.utils.Constants;
import com.bank.user.service.utils.MetadataContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/bank/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<User>> createNewUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        User createdUser = userService.createOrUpdateUser(userRegistrationRequest);
        return getSpecificResponse("New user created successfully.", Constants.CREATED_STATUS_CODE, createdUser);
    }

    @GetMapping("/get")
    public ResponseEntity<CommonResponse<Page<User>>> getUsers(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        Page<User> users = userService.getUsers(pageNumber, pageSize);
        return getSpecificResponse("Users fetched successfully.", Constants.OK_STATUS_CODE, users);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<CommonResponse<User>> getUserByUserId(@PathVariable("userId") String userId) {
        User user = userService.getUserById(userId);
        return getSpecificResponse("User fetched successfully.", Constants.OK_STATUS_CODE, user);
    }

    @PutMapping("/update")
    public ResponseEntity<CommonResponse<User>> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        User updatedUser = userService.createOrUpdateUser(updateUserRequest);
        return getSpecificResponse("User updated successfully.", Constants.OK_STATUS_CODE, updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "userId", required = true) String userId) {
        userService.deleteUser(userId);
        return getSpecificResponse("User deleted successfully", Constants.OK_STATUS_CODE, "NA");
    }

    @GetMapping("/status")
    public ResponseEntity<Object> getStatus() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("user service running up..");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private <T> ResponseEntity<CommonResponse<T>> getSpecificResponse(String msg, String statusCode, T payload) {
        try {
            CommonResponse<T> response = CommonResponse.<T>builder()
                    .timestamp(String.valueOf(LocalDateTime.now()))
                    .status(Constants.SUCCESS_TAG)
                    .statusCode(statusCode)
                    .message(msg)
                    .metadata(MetadataContext.getMetadata())
                    .payload(payload)
                    .build();
            return ResponseEntity.status(Integer.parseInt(statusCode)).body(response);
        } catch (Exception e) {
            throw new UserServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
    }
}
