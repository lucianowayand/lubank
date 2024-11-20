package io.github.lucianowayand.lubank.lubank.services.transaction;

import io.github.lucianowayand.lubank.lubank.models.transaction.CreateTransactionDto;
import io.github.lucianowayand.lubank.lubank.models.transaction.Transaction;
import io.github.lucianowayand.lubank.lubank.models.transaction.TransactionDto;
import io.github.lucianowayand.lubank.lubank.models.transaction.TransactionMapper;
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
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository repository, TransactionMapper transactionMapper) {
        this.repository = repository;
        this.transactionMapper = transactionMapper;
    }

    public List<TransactionDto> findByUserId(UUID userId) {
        List<Transaction> transactions = repository.findByUserId(userId);
        return transactions.stream()
                .map(transaction -> transactionMapper.toDto(transaction, userId))
                .toList();
    }

    public Float getUserBalance(UUID userId) {
        List<TransactionDto> transactions = this.findByUserId(userId);
        float balance = 0.0f;

        for (TransactionDto transaction : transactions) {
            if (transaction.getSenderId().equals(userId)) {
                balance -= transaction.getAmount();
            } else if (transaction.getReceiverId().equals(userId)) {
                balance += transaction.getAmount();
            }
        }

        return balance;
    }

    public void sendTransaction(User sender, User receiver, CreateTransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(dto.getAmount());
        transaction.setCreatedAt(new Date());

        repository.save(transaction);
    }
}
