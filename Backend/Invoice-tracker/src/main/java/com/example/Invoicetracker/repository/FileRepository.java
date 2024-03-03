package com.example.Invoicetracker.repository;

import com.example.Invoicetracker.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    @Query(nativeQuery = true, value = " " +
            " SELECT file.file_name, file.file_path, file.upload_date " +
            " FROM file " +
            " WHERE file.user_id = userId ")
    public List<File> getFileByUserId(long userId);

    @Query(nativeQuery = true, value = " " +
            " SELECT * " +
            " FROM file " +
            " WHERE file.file_name = fileName ")
    public Optional<File> getFileByName(String fileName);

    @Query(nativeQuery = true, value = " " +
            " SELECT f.id, f.file_name, f.file_path, f.upload_date, f.user_id" +
            " FROM file f" +
            " Order by f.upload_date DESC ")
    public List<File> getFilesOrderByLastCreated();

}
