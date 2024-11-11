package io.github.lucianowayand.lubank.lubank.models.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String govRegCode;

    @NotEmpty
    @Size(min = 6, message = "Minimum Password length is 6 characters")
    private String password;
}
