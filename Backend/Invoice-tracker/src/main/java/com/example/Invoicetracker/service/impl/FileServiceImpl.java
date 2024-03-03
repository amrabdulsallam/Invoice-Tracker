package com.example.Invoicetracker.service.impl;

import com.example.Invoicetracker.exception.FileNotFoundException;
import com.example.Invoicetracker.exception.UserNotFoundException;
import com.example.Invoicetracker.model.File;
import com.example.Invoicetracker.model.User;
import com.example.Invoicetracker.repository.FileRepository;
import com.example.Invoicetracker.repository.UserRepository;
import com.example.Invoicetracker.service.FileService;
import com.example.Invoicetracker.service.SaveFileToDirectoryService;
import com.example.Invoicetracker.service.dto.FileDTO;
import com.example.Invoicetracker.service.dto.FileReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private final UserRepository userRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, UserRepository userRepository) {
        super();
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @Value("/Users/amrabdulsalam/Desktop/invoices/")
    private String fileDirectory;

    @Override
    public FileReturnDTO saveFile(FileDTO file) throws IOException {
        User user = userRepository.findById(file.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found for the id : " + file.getUserId()));

        String newFilePath = SaveFileToDirectoryService.SaveFile(fileDirectory, file.getFile());
        File newFile = new File();

        newFile.setFileName(file.getFileName());
        newFile.setFilePath(newFilePath);
        newFile.setUser(user);

        fileRepository.save(newFile);

        return new FileReturnDTO(newFile);
    }

    @Override
    public List<FileReturnDTO> getFiles() {
        List<File> files = fileRepository.getFilesOrderByLastCreated();
        return files.stream().map(FileReturnDTO::new).collect(Collectors.toList());
    }

    @Override
    public FileReturnDTO getFileById(long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("User not found for the id : " + fileId));
        return new FileReturnDTO(file);
    }

    @Override
    public FileReturnDTO updateFile(FileDTO newFile, long id) throws IOException {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("User not found for the id : " + id));

        String newFilePath = SaveFileToDirectoryService.SaveFile(fileDirectory, newFile.getFile());

        file.setFileName(newFile.getFileName());
        file.setFilePath(newFilePath);

        fileRepository.save(file);

        return new FileReturnDTO(file);
    }

    @Override
    public void deleteFile(long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("User not found for the id : " + id));
        fileRepository.delete(file);
    }

}
