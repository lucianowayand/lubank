package io.github.lucianowayand.lubank.lubank.models.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty
    private String govRegCode;

    @NotEmpty
    private String password;
}
