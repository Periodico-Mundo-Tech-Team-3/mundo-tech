package com.mundotech.newspaper.service;

import org.springframework.web.multipart.MultipartFile;

import com.mundotech.newspaper.dto.response.FileUploadResponseDto;

public interface FileUploadService {
    FileUploadResponseDto upload(MultipartFile file);

}
