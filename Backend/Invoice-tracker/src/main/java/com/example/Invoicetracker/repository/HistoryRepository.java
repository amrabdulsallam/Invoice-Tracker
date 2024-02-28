package com.example.Invoicetracker.repository;

import com.example.Invoicetracker.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History,Long> {

    @Query(nativeQuery = true, value = " " +
            " SELECT * " +
            " FROM history " +
            " WHERE history.user_id = userId "
    )
    public Optional<List<History>> getUserHistory(long userId);

    @Query(nativeQuery = true, value = " " +
            " SELECT * " +
            " FROM history " +
            " WHERE history.file_id = fileId "
    )
    public Optional<List<History>> getFileHistory(long fileId);

}
