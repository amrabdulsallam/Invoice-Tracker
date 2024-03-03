package com.example.Invoicetracker.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class SaveFileToDirectoryService {

    public static String SaveFile(String directoryPath, MultipartFile file) throws IOException {
        String path = directoryPath + new Date() + file.getOriginalFilename();
        File myFile = new File(path);
        myFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();

        return path;
    }

}
