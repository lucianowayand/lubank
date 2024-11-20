package io.github.lucianowayand.lubank.lubank.models.transaction;

import io.github.lucianowayand.lubank.lubank.models.transaction.Transaction;
import io.github.lucianowayand.lubank.lubank.models.transaction.TransactionDto;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.UUID;

@Component
public class TransactionMapper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    public TransactionDto toDto(Transaction transaction, UUID currentUserId) {
        TransactionDto dto = new TransactionDto();

        dto.setId(transaction.getId());
        dto.setCreatedAt(DATE_FORMAT.format(transaction.getCreatedAt()));
        dto.setAmount(transaction.getAmount());

        dto.setSenderId(transaction.getSender().getId());
        dto.setReceiverId(transaction.getReceiver().getId());

        dto.setSenderName(transaction.getSender().getName());
        dto.setSenderGovRegCode(transaction.getSender().getGovRegCode());

        dto.setReceiverName(transaction.getReceiver().getName());
        dto.setReceiverGovRegCode(transaction.getReceiver().getGovRegCode());

        if (transaction.getSender().getId().equals(currentUserId)) {
            dto.setUserName(transaction.getReceiver().getName());
            dto.setUserGovRegCode(transaction.getReceiver().getGovRegCode());
        } else if (transaction.getReceiver().getId().equals(currentUserId)) {
            dto.setUserName(transaction.getSender().getName());
            dto.setUserGovRegCode(transaction.getSender().getGovRegCode());
        }

        return dto;
    }
}
