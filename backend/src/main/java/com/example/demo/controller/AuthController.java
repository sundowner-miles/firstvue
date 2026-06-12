package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.PasswordUpdateRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<UserAccount> login(@RequestBody LoginRequest request) {
        UserAccount user = authService.login(request);
        if (user == null) {
            return Result.fail("用户名或密码错误");
        }
        return Result.success(user);
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterRequest request) {
        boolean success = userService.register(request);
        if (!success) {
            return Result.fail("用户名已存在或信息不完整");
        }
        return Result.success("注册成功", "注册成功");
    }

    @PutMapping("/password")
    public Result<String> updatePassword(@RequestBody PasswordUpdateRequest request) {
        boolean success = userService.updatePassword(
                request.getUsername(),
                request.getOldPwd(),
                request.getNewPwd()
        );
        if (!success) {
            return Result.fail("用户名或旧密码错误");
        }
        return Result.success("修改成功", "修改成功");
    }
}
