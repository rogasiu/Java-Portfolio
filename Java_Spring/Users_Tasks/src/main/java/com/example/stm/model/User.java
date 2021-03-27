package com.example.stm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String name;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean status = false;

    @Column(name = "registration_time")
    private LocalDateTime registrationDateTime = LocalDateTime.now();

    //uzytkownik moze miec wiele zadan

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL // zawsze po stronie rodzica!
    )
    @JsonIgnoreProperties({"author"})
    private List<Task> tasks = new ArrayList<>();

    public User(String name, String lastName, String email, String password, boolean status) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.status = status;
    }


}
