package io.github.lucianowayand.lubank.lubank.models.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateTransactionDto {
    @NotNull
    private UUID receiverId;

    @NotNull
    @Positive
    private Float amount;
}
