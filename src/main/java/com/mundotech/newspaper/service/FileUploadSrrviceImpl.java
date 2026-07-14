package com.mundotech.newspaper.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.StringUtils;

import com.mundotech.newspaper.dto.response.FileUploadResponseDto;


//import  com.mundotech.newspaper.service.FileUploadService;

@Service
public class FileUploadSrrviceImpl implements FileUploadService {

    @Value("${app.upload.path}")
    private String uploadPath;    
    @Override
    public FileUploadResponseDto upload(MultipartFile file) {

        try {

            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

            String filename = UUID.randomUUID() + "_" + originalFilename;

            Path directory = Paths.get(uploadPath);

            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            Path destination = directory.resolve(filename);

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return new FileUploadResponseDto(
                    filename,
                    file.getContentType(),
                    file.getSize(),
                    destination.toString()
            );

        } catch (IOException e) {
            throw new RuntimeException("Error uploading file", e);
        }
       
    }

}
