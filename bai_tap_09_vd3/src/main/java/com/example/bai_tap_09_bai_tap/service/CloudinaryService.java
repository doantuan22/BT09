package com.example.bai_tap_09_bai_tap.service;
import org.springframework.web.multipart.MultipartFile;
public interface CloudinaryService { CloudinaryUploadResult upload(MultipartFile file); void delete(String publicId); }
