package com.mundotech.newspaper.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mundotech.newspaper.dto.response.FileUploadResponseDto;
import com.mundotech.newspaper.service.FileUploadService;

@RestController
@RequestMapping("/api/files")

public class FileUploadController {
    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponseDto> upload(@RequestParam("file") MultipartFile file) {

        FileUploadResponseDto response = fileUploadService.upload(file);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
