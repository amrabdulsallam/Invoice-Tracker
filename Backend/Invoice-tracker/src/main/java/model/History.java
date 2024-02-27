package model;

import jakarta.persistence.*;
import lombok.Data;
import model.enums.Status;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "history")
public class History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @CreationTimestamp
    @Column(name = "history_date")
    private Date historyDate;

    @Override
    public String toString() {
        return "Status : " + this.status + " , " + "Date : " + this.historyDate;
    }

}
