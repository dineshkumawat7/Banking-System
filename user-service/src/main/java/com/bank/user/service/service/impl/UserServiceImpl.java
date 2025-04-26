package com.bank.user.service.service.impl;

import com.bank.user.service.config.EnvironmentParamConfig;
import com.bank.user.service.entity.User;
import com.bank.user.service.exception.UserServiceException;
import com.bank.user.service.model.UpdateUserRequest;
import com.bank.user.service.model.UserRegistrationRequest;
import com.bank.user.service.repository.UserRepo;
import com.bank.user.service.service.UserService;
import com.bank.user.service.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EnvironmentParamConfig env;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User createOrUpdateUser(Object userRequest) {
        try {
            Optional<User> existingUser = Optional.empty();
            if (userRequest instanceof UserRegistrationRequest userRegistrationRequest) {
                existingUser = userRepo.findByEmail(userRegistrationRequest.getEmail());
                if (existingUser.isPresent()) {
                    throw new UserServiceException(Constants.CONFLICT_STATUS_CODE, "An account with this email already exists. Please log in or use a different email.");
                }
                User newUser = new User();
                newUser.setUserId(UUID.randomUUID().toString().replace("-", ""));
                newUser.setFirstName(userRegistrationRequest.getFirstName());
                newUser.setLastName(userRegistrationRequest.getLastName());
                newUser.setEmail(userRegistrationRequest.getEmail());
                newUser.setMobNumber(userRegistrationRequest.getMobNumber());
                newUser.setPassword(userRegistrationRequest.getPassword());
                newUser.setCreatedAt(String.valueOf(LocalDateTime.now()));
                newUser.setUpdatedAt(String.valueOf(LocalDateTime.now()));
                newUser.setCreatedBy("User");
                newUser.setUpdateBy("User");
                newUser.setActive(true);
                return userRepo.save(newUser);
            } else if (userRequest instanceof UpdateUserRequest udateUpdateUserRequest) {
                existingUser = userRepo.findByEmail(udateUpdateUserRequest.getEmail());
                if (existingUser.isPresent()) {
                    User existsUser = existingUser.get();
                    existsUser.setFirstName(udateUpdateUserRequest.getFirstName());
                    existsUser.setLastName(udateUpdateUserRequest.getLastName());
                    existsUser.setEmail(udateUpdateUserRequest.getEmail());
                    existsUser.setMobNumber(udateUpdateUserRequest.getMobNumber());
                    existsUser.setUpdatedAt(String.valueOf(LocalDateTime.now()));
                    existsUser.setCreatedBy("User");
                    existsUser.setUpdateBy("User");
                    existsUser.setActive(true);
                    return userRepo.save(existsUser);
                }
            }
            throw new UserServiceException(Constants.BAD_REQUEST_STATUS_CODE, "Unsupported request type. Please provide a valid registration or update request.");
        } catch (UserServiceException e) {
            throw new UserServiceException(e.getMsgCode(), e.getMessage());
        } catch (Exception e) {
            throw new UserServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
    }

    @Override
    public User createNewUser(UserRegistrationRequest userRegistrationRequest) {
        try {
            if (userRegistrationRequest == null) {
                throw new UserServiceException(Constants.BAD_REQUEST_STATUS_CODE, "Request body can not be null or empty.");
            }
            User user = new User();
            user.setUserId(UUID.randomUUID().toString().replace("-", ""));
            user.setFirstName(userRegistrationRequest.getFirstName());
            user.setLastName(userRegistrationRequest.getLastName());
            user.setEmail(userRegistrationRequest.getEmail());
            user.setMobNumber(userRegistrationRequest.getMobNumber());
            user.setPassword(userRegistrationRequest.getPassword());
            user.setCreatedAt(String.valueOf(LocalDateTime.now()));
            user.setUpdatedAt(String.valueOf(LocalDateTime.now()));
            user.setActive(true);
            return userRepo.save(user);
        } catch (UserServiceException e) {
            throw new UserServiceException(e.getMsgCode(), e.getMessage());
        } catch (Exception e) {
            throw new UserServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
    }

    @Override
    public User getUserById(String userId) {
        try {
            return userRepo.findById(userId).orElseThrow(() -> new UserServiceException(Constants.NOT_FOUND_STATUS_CODE, String.format("User not found with id : %s", userId)));
        } catch (UserServiceException e) {
            throw new UserServiceException(e.getMsgCode(), e.getMessage());
        } catch (Exception e) {
            throw new UserServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
    }

    @Override
    public Page<User> getUsers(int pageNumber, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return userRepo.findAll(pageable);
        } catch (UserServiceException e) {
            throw new UserServiceException(e.getMsgCode(), e.getMessage());
        } catch (Exception e) {
            throw new UserServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
    }

    @Override
    public void deleteUser(String userId) {
        try {
            User user = userRepo.findById(userId).orElseThrow(() -> new UserServiceException(Constants.NOT_FOUND_STATUS_CODE, String.format("User not found with id : %s", userId)));
            userRepo.delete(user);
        } catch (UserServiceException e) {
            throw new UserServiceException(e.getMsgCode(), e.getMessage());
        } catch (Exception e) {
            throw new UserServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
    }
}
