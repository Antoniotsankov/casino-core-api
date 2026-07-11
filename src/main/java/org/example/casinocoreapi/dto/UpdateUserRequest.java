package org.example.casinocoreapi.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.casinocoreapi.enums.UserStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "Request for updating an existing user")
public class UpdateUserRequest {

    @Schema(
            description = "Player username",
            example = "Antonio"
    )
    private String username;

    @Schema(
            description = "Player country",
            example = "Bulgaria"
    )
    private String country;

    @Schema(
            description = "Current user status",
            example = "ACTIVE"
    )
    private UserStatus status;
}
