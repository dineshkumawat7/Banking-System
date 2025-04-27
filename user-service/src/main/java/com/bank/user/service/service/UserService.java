package com.bank.user.service.service;

import com.bank.user.service.entity.User;
import com.bank.user.service.model.UserRegistrationRequest;
import org.springframework.data.domain.Page;

public interface UserService {

    User createOrUpdateUser(Object userRequest);

    User createNewUser(UserRegistrationRequest userRegistrationRequest);

    User getUserById(String userId);

    Page<User> getUsers(int pageNumber, int pageSize);

    void deleteUser(String userId);
}
