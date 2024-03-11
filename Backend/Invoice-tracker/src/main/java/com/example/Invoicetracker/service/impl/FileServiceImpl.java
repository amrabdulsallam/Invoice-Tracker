package com.example.Invoicetracker.service.impl;

import com.example.Invoicetracker.exception.FileNotFoundException;
import com.example.Invoicetracker.model.File;
import com.example.Invoicetracker.repository.FileRepository;
import com.example.Invoicetracker.repository.InvoiceRepository;
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


    private final InvoiceRepository invoiceRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, InvoiceRepository invoiceRepository) {
        super();
        this.fileRepository = fileRepository;
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * The directory where files/images will be saved
     */
    @Value("/Users/amrabdulsalam/Desktop/invoices/")
    private String fileDirectory;

    /**
     * This function saves files to directory and save the path to DB
     * @param file The DTO file that will be sent
     * @return FileReturnDTO contains the metadata about the stored file
     * @throws IOException if an error occurs while saving the file
     */
    @Override
    public FileReturnDTO saveFile(FileDTO file) throws IOException {
        String newFilePath = SaveFileToDirectoryService.SaveFile(fileDirectory, file.getFile());
        File newFile = new File();

        newFile.setFileName(file.getFileName());
        newFile.setFilePath(newFilePath);

        fileRepository.save(newFile);

        return new FileReturnDTO(newFile);
    }

    /**
     * Retrieves all files stored in the DB
     * @return a list of FileReturnDTO which is a list of all the metadata about all the files
     */
    @Override
    public List<FileReturnDTO> getFiles() {
        List<File> files = fileRepository.getFilesOrderByLastCreated();
        return files.stream().map(FileReturnDTO::new).collect(Collectors.toList());
    }

    /**
     * Retrieves a file by its ID
     * @param fileId the ID of the file
     * @return  FileReturnDTO with metadata about the selected file
     * @throws FileNotFoundException if no file found in the DB
     */
    @Override
    public FileReturnDTO getFileById(long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("User not found for the id : " + fileId));
        return new FileReturnDTO(file);
    }

    /**
     * Update file with a new metadata
     * @param newFile the updated fileDTO data
     * @param id the file id in the DB
     * @return The updated metadata of the file
     * @throws IOException if an error occurs while saving the file
     */
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

    /**
     * Delete a file by its ID
     * @param id
     * @throws FileNotFoundException If the file not found.
     */
    @Override
    public void deleteFile(long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("User not found for the id : " + id));
        fileRepository.delete(file);
    }

}
