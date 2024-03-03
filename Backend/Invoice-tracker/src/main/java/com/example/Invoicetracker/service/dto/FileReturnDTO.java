package com.example.Invoicetracker.service.dto;

import com.example.Invoicetracker.model.File;
import lombok.Data;

import java.util.Date;

@Data
public class FileReturnDTO {

    private long id;

    private String fileName;

    private String filePath;

    private Date uploadDate;

    public FileReturnDTO(File file) {
        this.id = file.getId();
        this.fileName = file.getFileName();
        this.filePath = file.getFilePath();
        this.uploadDate = file.getUploadDate();
    }

}
