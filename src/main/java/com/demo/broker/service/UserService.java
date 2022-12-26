package com.demo.broker.service;

import com.demo.broker.dto.UserDto;
import com.demo.broker.dto.UserNewDto;
import com.demo.broker.exception.UserAlreadyExistsException;
import com.demo.broker.exception.UserNotFoundException;
import com.demo.broker.mapper.UserMapper;
import com.demo.broker.model.User;
import com.demo.broker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void create(UserNewDto userDto) throws UserAlreadyExistsException {
        if (isUserExists(userDto.userName())) {
            throw new UserAlreadyExistsException(String.format("User '%s' is already registered",
                    userDto.userName()));
        }
        userRepository.add(userDto.userName(), new User(userDto.amount()));
        LOGGER.info("New user '{}' saved successfully", userDto.userName());
    }

    public UserDto get(String userName) throws UserNotFoundException {
        User user = userRepository.get(userName).orElseThrow(
                () -> new UserNotFoundException(String.format("User '%s' not found", userName)));
        LOGGER.info("User '{}' found in the repository", userName);
        return userMapper.userToUserDto(user);
    }

    public boolean isUserExists(String userName) {
        return userRepository.isUserExists(userName);
    }

}
