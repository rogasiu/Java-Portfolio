package com.example.stm.model;

import com.example.stm.model.enums.Status;
import com.example.stm.model.enums.Types;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    private String title;

    @Type(type = "text")
    private String description;


    @Column(name = "date_added")
    private LocalDateTime dateAdded = LocalDateTime.now();

    @Enumerated(value = EnumType.STRING)
    private Types types;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    //kazde zadanie posiada jednego autora
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JsonIgnoreProperties({"tasks"})
    private User author;

    public Task(String title, String description, Types types, Status status, User author) {
        this.title = title;
        this.description = description;
        this.types = types;
        this.status = status;
        this.author = author;
    }
}
