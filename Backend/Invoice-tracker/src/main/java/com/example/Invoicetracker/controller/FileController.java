package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.service.FileService;
import com.example.Invoicetracker.service.dto.FileDTO;
import com.example.Invoicetracker.service.dto.FileReturnDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/files")
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        super();
        this.fileService = fileService;
    }

    @PostMapping()
    public ResponseEntity<?> saveFile(@ModelAttribute FileDTO file) {
        try {
            logger.info("File uploaded with info : " + file);
            return ResponseEntity.status(HttpStatus.CREATED).body(fileService.saveFile(file));
        } catch (Exception e) {
            logger.error("Failed to upload file : {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
        }
    }

    @GetMapping()
    public ResponseEntity<List<FileReturnDTO>> getAllFiles() {
        logger.info("Attempt to get all files");
        return ResponseEntity.status(HttpStatus.OK).body(fileService.getFiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFileById(@PathVariable long id) {
        try {
            logger.info("Attempt to get all file with id " + id);
            FileReturnDTO file = fileService.getFileById(id);
            logger.info("Successful getting file with id " + id);
            return ResponseEntity.status(HttpStatus.OK).body(file);
        } catch (Exception e) {
            logger.error("Failed to get file : {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No file found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFile(@ModelAttribute FileDTO newFile, @PathVariable long id) {
        try {
            logger.info("Attempt to update file with id " + id);
            return ResponseEntity.status(HttpStatus.OK).body(fileService.updateFile(newFile, id));
        } catch (Exception e) {
            logger.error("Failed to update file : {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No file found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable long id) {
        try {
            logger.info("Attempt to delete file with id " + id);
            fileService.deleteFile(id);
            logger.info("file deleted with id " + id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            logger.error("Failed to delete file : {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No file found");
        }
    }

}
