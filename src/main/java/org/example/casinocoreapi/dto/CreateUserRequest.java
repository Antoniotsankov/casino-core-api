package org.example.casinocoreapi.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request for creating a new casino user")
public class CreateUserRequest {

    @Schema(
            description = "Unique member identifier",
            example = "MEM-1001"
    )
    @NotBlank(message = "Member ID is required")
    private String memberId;

    @Schema(
            description = "Player username",
            example = "Antonio"
    )
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(
            description = "Player country",
            example = "Bulgaria"
    )
    @NotBlank(message = "Country is required")
    private String country;
}
