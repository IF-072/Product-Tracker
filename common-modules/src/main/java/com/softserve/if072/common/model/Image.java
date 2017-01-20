package com.softserve.if072.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

public class Image {

    private int id;
    private String fileName;
    private String contentType;
    private MultipartFile multipartFile;
    private byte[] imageData;


    public Image() {

    }

    public Image(int id, String fileName, String contentType, MultipartFile multipartFile, byte[] imageData) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.multipartFile = multipartFile;
        this.imageData = imageData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @JsonIgnore
    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
