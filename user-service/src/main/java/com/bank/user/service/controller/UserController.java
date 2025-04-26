package com.bank.user.service.controller;

import com.bank.user.service.entity.User;
import com.bank.user.service.exception.UserServiceException;
import com.bank.user.service.model.UpdateUserRequest;
import com.bank.user.service.model.UserRegistrationRequest;
import com.bank.user.service.model.common.CommonResponse;
import com.bank.user.service.service.UserService;
import com.bank.user.service.utils.Constants;
import com.bank.user.service.utils.MetadataContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create new user", description = "This API is used to create a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New user created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<User>> createNewUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        User createdUser = userService.createOrUpdateUser(userRegistrationRequest);
        return getSpecificResponse("New user created successfully.", Constants.CREATED_STATUS_CODE, createdUser);
    }

    @Operation(summary = "Fetch all users", description = "This API retrieves a paginated list of users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<Page<User>>> getUsers(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        Page<User> users = userService.getUsers(pageNumber, pageSize);
        return getSpecificResponse("Users fetched successfully.", Constants.OK_STATUS_CODE, users);
    }

    @Operation(summary = "Fetch user by Id", description = "This API retrieves user details by user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User fetched successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/get/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<User>> getUserByUserId(@PathVariable("userId") String userId) {
        User user = userService.getUserById(userId);
        return getSpecificResponse("User fetched successfully.", Constants.OK_STATUS_CODE, user);
    }

    @Operation(summary = "Update user", description = "This API updates an existing user's information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<User>> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        User updatedUser = userService.createOrUpdateUser(updateUserRequest);
        return getSpecificResponse("User updated successfully.", Constants.OK_STATUS_CODE, updatedUser);
    }

    @Operation(summary = "Delete user", description = "This API deletes a user by user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@RequestParam(value = "userId", required = true) String userId) {
        userService.deleteUser(userId);
        return getSpecificResponse("User deleted successfully", Constants.OK_STATUS_CODE, "NA");
    }

    @Operation(summary = "Check service status", description = "This API checks if the User service is running.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service is running"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
