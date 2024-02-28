package com.example.Invoicetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "file")
public class File implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @CreationTimestamp
    @Column(name = "upload_date")
    private Date uploadDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "fileHistory", cascade = CascadeType.ALL)
    private List<History> histories;

    @Override
    public String toString() {
        return "File Name: " + this.fileName + " , " + "File Path: " + this.filePath + " , " +
                "Upload Date: " + this.uploadDate;
    }

}
