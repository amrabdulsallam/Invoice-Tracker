package com.example.Invoicetracker.service;

import com.example.Invoicetracker.model.File;
import com.example.Invoicetracker.service.dto.FileDTO;
import com.example.Invoicetracker.service.dto.FileReturnDTO;

import java.io.IOException;
import java.util.List;

public interface FileService {

    FileReturnDTO saveFile(FileDTO file) throws IOException;

    List<FileReturnDTO> getFiles();

    FileReturnDTO getFileById(long fileId);

    FileReturnDTO updateFile(FileDTO file, long id) throws IOException;

    void deleteFile(long id);

}
