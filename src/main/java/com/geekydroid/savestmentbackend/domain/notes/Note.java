package com.geekydroid.savestmentbackend.domain.notes;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "svmt_notes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_at")
    private Instant updatedAt;
}
