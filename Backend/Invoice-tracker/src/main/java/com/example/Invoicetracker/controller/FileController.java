package com.example.Invoicetracker.controller;

import com.example.Invoicetracker.service.FileService;
import com.example.Invoicetracker.service.dto.FileDTO;
import com.example.Invoicetracker.service.dto.FileReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        super();
        this.fileService = fileService;
    }

    @PostMapping()
    public ResponseEntity<?> saveFile(@ModelAttribute FileDTO file) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(fileService.saveFile(file));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
        }
    }

    @GetMapping()
    public ResponseEntity<List<FileReturnDTO>> getAllFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.getFiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFileById(@PathVariable long id) {
        try {
            FileReturnDTO file = fileService.getFileById(id);
            return ResponseEntity.status(HttpStatus.OK).body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No file found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFile(@ModelAttribute FileDTO newFile, @PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(fileService.updateFile(newFile, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No file found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No file found");
        }
    }

}
