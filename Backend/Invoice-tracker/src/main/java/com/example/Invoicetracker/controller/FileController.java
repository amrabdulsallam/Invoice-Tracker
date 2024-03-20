package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.controller.entity.ResponseEntity;
import com.example.Invoicetracker.service.FileService;
import com.example.Invoicetracker.service.dto.FileDTO;
import com.example.Invoicetracker.service.dto.FileReturnDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    @PreAuthorize("hasAnyRole('SUPER_USER', 'USER')")
    public ResponseEntity<?> saveFile(@ModelAttribute FileDTO file) throws IOException {
        logger.info("File uploaded with info : " + file);
        return new ResponseEntity<>(fileService.saveFile(file));
    }

    @GetMapping()
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<List<FileReturnDTO>> getAllFiles() {
        logger.info("Attempt to get all files");
        return new ResponseEntity<>(fileService.getFiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFileById(@PathVariable long id) {
        logger.info("Attempt to get all file with id " + id);
        FileReturnDTO file = fileService.getFileById(id);
        logger.info("Successful getting file with id " + id);
        return new ResponseEntity<>(file);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_USER', 'USER')")
    public ResponseEntity<?> updateFile(@ModelAttribute FileDTO newFile, @PathVariable long id) throws IOException {
        logger.info("Attempt to update file with id " + id);
        return new ResponseEntity<>(fileService.updateFile(newFile, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_USER', 'USER')")
    public ResponseEntity<?> deleteFile(@PathVariable long id) {
        logger.info("Attempt to delete file with id " + id);
        fileService.deleteFile(id);
        logger.info("file deleted with id " + id);
        return new ResponseEntity<>();
    }

}
