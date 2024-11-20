package io.github.lucianowayand.lubank.lubank.repositories.transaction;

import io.github.lucianowayand.lubank.lubank.models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT * FROM transactions WHERE sender_id=?",nativeQuery = true)
    public Transaction findBySenderId(UUID username);

    @Query(value = "SELECT * FROM transactions WHERE receiver_id=?", nativeQuery = true)
    public Transaction findByReceiverId(UUID username);

    @Query(value = "SELECT * FROM transactions WHERE sender_id=:userId OR receiver_id=:userId", nativeQuery = true)
    public List<Transaction> findByUserId(@Param("userId") UUID username);
}