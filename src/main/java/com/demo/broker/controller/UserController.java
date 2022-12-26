package com.demo.broker.controller;

import com.demo.broker.dto.UserDto;
import com.demo.broker.dto.UserNewDto;
import com.demo.broker.exception.UserAlreadyExistsException;
import com.demo.broker.exception.UserNotFoundException;
import com.demo.broker.service.UserService;
import com.demo.broker.utils.ErrorConverter;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final ErrorConverter errorConverter;
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(ErrorConverter errorConverter,
                          UserService userService) {
        this.errorConverter = errorConverter;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody String userName,
                                          HttpSession httpSession) {
        if (userService.isUserExists(userName)) {
            httpSession.setAttribute("user", userName);
            LOGGER.info("POST /api/user/login {}", HttpStatus.OK);
            return ResponseEntity.ok().build();
        } else {
            LOGGER.warn("POST /api/user/login {}", HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserNewDto userDto,
                                             BindingResult bindingResult,
                                             HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            LOGGER.warn("POST /api/user/register {}", HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(
                    errorConverter.convertFieldErrorListToMap(bindingResult.getFieldErrors()));
        }
        if ("admin".equals(httpSession.getAttribute("user"))) {
            try {
                userService.create(userDto);
                LOGGER.info("POST /api/user/register {}", HttpStatus.OK);
                return ResponseEntity.ok().build();
            } catch (UserAlreadyExistsException e) {
                LOGGER.warn("POST /api/user/register {}", HttpStatus.CONFLICT);
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
        } else {
            LOGGER.warn("POST /api/user/register {}", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>("Authorization failed. " +
                    "You need to log in as an administrator", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/get-user-info")
    public ResponseEntity<?> getUser(HttpSession httpSession) {
        String userName = (String) httpSession.getAttribute("user");
        UserDto userDto;
        try {
            userDto = userService.get(userName);
            LOGGER.info("GET /api/user/get-user-info {}", HttpStatus.OK);
            return ResponseEntity.ok(userDto);
        } catch (UserNotFoundException e) {
            LOGGER.warn("GET /api/user/get-user-info {}", HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
