package com.cers.bulletinBoard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "publication_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;

    @Column(name = "visualization_date")
    private LocalDate visualizationDate;

}
