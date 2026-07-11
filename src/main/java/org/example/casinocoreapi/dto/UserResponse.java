package org.example.casinocoreapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.example.casinocoreapi.enums.UserStatus;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "username",
        "memberId",
        "country",
        "status",
        "createdAt"
})
@Getter
@Setter
@Schema(description = "Response containing casino user information")
public class UserResponse {

    @Schema(description = "User identifier", example = "1")
    private Long id;

    @Schema(description = "Username", example = "Antonio")
    private String username;

    @Schema(description = "Unique member identifier", example = "MEM-1001")
    private String memberId;

    @Schema(description = "User country", example = "Bulgaria")
    private String country;

    @Schema(description = "Current user status", example = "ACTIVE")
    private UserStatus status;

    @Schema(description = "User creation date and time", example = "2026-07-09T12:30:45")
    private LocalDateTime createdAt;
}