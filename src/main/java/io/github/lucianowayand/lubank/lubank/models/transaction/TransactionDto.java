package io.github.lucianowayand.lubank.lubank.models.transaction;

import lombok.Data;

import java.util.UUID;

@Data
public class TransactionDto {
    private UUID id;
    private String createdAt;
    private UUID senderId;
    private String senderName;
    private String senderGovRegCode;
    private UUID receiverId;
    private String receiverName;
    private String receiverGovRegCode;
    private float amount;

    // Opposite user fields to be populated based on who made the request
    private String userName;
    private String userGovRegCode;
}
