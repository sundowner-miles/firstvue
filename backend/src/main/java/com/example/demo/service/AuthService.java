package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.UserAccount;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public UserAccount login(LoginRequest request) {
        UserAccount user = userService.findByUsername(request.getUsername());
        if (user == null) {
            return null;
        }
        if (!user.getPassword().equals(request.getPassword())) {
            return null;
        }
        return copyWithoutPassword(user);
    }

    private UserAccount copyWithoutPassword(UserAccount source) {
        UserAccount result = new UserAccount();
        result.setUsername(source.getUsername());
        result.setEmail(source.getEmail());
        result.setBirthday(source.getBirthday());
        result.setAvatar(source.getAvatar());
        return result;
    }
}
