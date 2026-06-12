package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.UserItem;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public Result<List<UserItem>> list() {
        return Result.success(userService.getList());
    }
}
