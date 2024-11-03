package io.github.lucianowayand.lubank.lubank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}