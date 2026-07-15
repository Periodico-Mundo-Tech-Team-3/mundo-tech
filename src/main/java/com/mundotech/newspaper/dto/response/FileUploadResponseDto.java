package com.mundotech.newspaper.dto.response;

//si se cambia a record, se debe cambiar el constructor y los getters y setters
//quedaria un record FileUploadResponseDto(String fileName, String contentType, long size, String path) {}
public class FileUploadResponseDto {
    private String fileName;
    private String contentType;
    private long size;
    private String path;

    public FileUploadResponseDto() {
    }

    public FileUploadResponseDto(String fileName, String contentType, long size, String path) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
        this.path = path;
    }

        public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
