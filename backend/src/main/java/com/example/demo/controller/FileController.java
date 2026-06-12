package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            String avatarUrl = fileService.storeAvatar(file);
            return Result.success(avatarUrl);
        } catch (IllegalArgumentException e) {
            return Result.fail(e.getMessage());
        } catch (IOException e) {
            return Result.fail("头像保存失败：" + e.getMessage());
        } catch (Exception e) {
            return Result.fail("头像上传失败：" + e.getMessage());
        }
    }
}
