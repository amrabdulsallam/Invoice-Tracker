package com.example.Invoicetracker.service.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDTO {

    private String fileName;

    private MultipartFile file;

}
