package io.github.lucianowayand.lubank.lubank.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false, name = "gov_reg_code")
    private String govRegCode;
}