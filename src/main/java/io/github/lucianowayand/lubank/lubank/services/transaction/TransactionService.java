package io.github.lucianowayand.lubank.lubank.services.transaction;

import io.github.lucianowayand.lubank.lubank.models.transaction.CreateTransactionDto;
import io.github.lucianowayand.lubank.lubank.models.transaction.Transaction;
import io.github.lucianowayand.lubank.lubank.models.user.User;
import io.github.lucianowayand.lubank.lubank.repositories.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    public Float getUserBalance(UUID userId) {
        List<Transaction> transactions = this.findByUserId(userId);
        float balance = 0.0f;

        for (Transaction transaction : transactions) {
            if (transaction.getSenderId().equals(userId)) {
                balance -= transaction.getAmount();
            } else if (transaction.getReceiverId().equals(userId)) {
                balance += transaction.getAmount();
            }
        }

        return balance;
    }

    public void sendTransaction(UUID senderId, CreateTransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(dto.getReceiverId());
        transaction.setAmount(dto.getAmount());
        transaction.setCreatedAt(new Date());

        repository.save(transaction);
    }
}
