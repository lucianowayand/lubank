package io.github.lucianowayand.lubank.lubank.models.transaction;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, name="created_at")
    private Date createdAt;

    @Column(nullable = false)
    private UUID senderId;

    @Column(nullable = false)
    private UUID receiverId;

    @Column(nullable = false)
    private float amount;
}
