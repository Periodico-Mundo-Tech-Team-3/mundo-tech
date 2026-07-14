package com.mundotech.newspaper.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.StringUtils;

import com.mundotech.newspaper.dto.response.FileUploadResponseDto;


@Service
public class FileUploadSrrviceImpl implements FileUploadService {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "webp", "gif");
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/webp", "image/gif");

    @Value("${app.upload.path}")
    private String uploadPath;    
    @Override
    public FileUploadResponseDto upload(MultipartFile file) {

        // 1. VALIDACIONES BÁSICAS
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío o no ha sido enviado.");
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFilename == null) {
            throw new IllegalArgumentException("Nombre de archivo inválido.");
        }

        // --- VALIDACIÓN DE EXTENSIÓN ---
        String extension = "";
        int lastIndexOf = originalFilename.lastIndexOf(".");
        if (lastIndexOf != -1) {
            extension = originalFilename.substring(lastIndexOf + 1).toLowerCase();
        }

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                "Extensión '." + extension + "' no permitida. Solo se aceptan: " + ALLOWED_EXTENSIONS
            );
        }

        // --- VALIDACIÓN DE MIME TYPE ---
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException(
                "Tipo de contenido '" + contentType + "' no permitido. Solo se aceptan imágenes."
            );
        }

        try {

            //String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

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
