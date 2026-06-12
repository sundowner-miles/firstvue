package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class FileService {

    private static final long MAX_AVATAR_SIZE = 150L * 1024 * 1024;

    private static final Set<String> ALLOWED_TYPES = new HashSet<>(Arrays.asList(
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/gif",
            "image/webp",
            "image/bmp",
            "image/svg+xml",
            "image/tiff",
            "image/x-icon",
            "image/vnd.microsoft.icon",
            "image/avif",
            "image/heic",
            "image/heif"
    ));

    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(Arrays.asList(
            ".jpg", ".jpeg", ".png", ".gif", ".webp", ".bmp",
            ".svg", ".tif", ".tiff", ".ico", ".avif", ".heic", ".heif"
    ));

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public String storeAvatar(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("请选择要上传的头像");
        }

        if (file.getSize() > MAX_AVATAR_SIZE) {
            throw new IllegalArgumentException("头像大小不能超过 150MB");
        }

        if (!isAllowedImage(file)) {
            throw new IllegalArgumentException("仅支持 JPG、PNG、GIF、WEBP、BMP、SVG、TIFF、ICO、AVIF、HEIC 等常见图片格式");
        }

        String contentType = file.getContentType();
        String extension = getExtension(file.getOriginalFilename(), contentType);
        String filename = UUID.randomUUID().toString().replace("-", "") + extension;

        Path avatarDir = Paths.get(uploadDir, "avatars").toAbsolutePath().normalize();
        Files.createDirectories(avatarDir);
        Path target = avatarDir.resolve(filename);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
        }

        return "/api/files/avatars/" + filename;
    }

    private boolean isAllowedImage(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null && ALLOWED_TYPES.contains(contentType.toLowerCase(Locale.ROOT))) {
            return true;
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.contains(".")) {
            return false;
        }

        String extension = originalName.substring(originalName.lastIndexOf('.')).toLowerCase(Locale.ROOT);
        return ALLOWED_EXTENSIONS.contains(extension);
    }

    private String getExtension(String originalName, String contentType) {
        if (originalName != null && originalName.contains(".")) {
            String extension = originalName.substring(originalName.lastIndexOf('.')).toLowerCase(Locale.ROOT);
            if (ALLOWED_EXTENSIONS.contains(extension)) {
                return extension;
            }
        }
        if (contentType == null) {
            return ".jpg";
        }
        switch (contentType.toLowerCase(Locale.ROOT)) {
            case "image/png":
                return ".png";
            case "image/gif":
                return ".gif";
            case "image/webp":
                return ".webp";
            case "image/bmp":
                return ".bmp";
            case "image/svg+xml":
                return ".svg";
            case "image/tiff":
                return ".tiff";
            case "image/x-icon":
            case "image/vnd.microsoft.icon":
                return ".ico";
            case "image/avif":
                return ".avif";
            case "image/heic":
                return ".heic";
            case "image/heif":
                return ".heif";
            default:
                return ".jpg";
        }
    }
}
