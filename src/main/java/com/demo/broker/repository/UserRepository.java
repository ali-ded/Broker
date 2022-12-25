package com.demo.broker.repository;

import com.demo.broker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final Map<String, User> userMap;

    @Autowired
    public UserRepository() {
        userMap = new HashMap<>();
        userMap.put("admin", null);
    }

    public void add(String userName, User userAccount) {
        userMap.put(userName, userAccount);
    }

    public Optional<User> get(String userName) {
        return Optional.ofNullable(userMap.get(userName));
    }
}
