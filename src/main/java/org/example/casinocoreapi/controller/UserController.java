package org.example.casinocoreapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.casinocoreapi.dto.CreateUserRequest;
import org.example.casinocoreapi.dto.UserResponse;
import org.example.casinocoreapi.enums.UserStatus;
import org.example.casinocoreapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.example.casinocoreapi.dto.UpdateUserRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
@RestController
@Tag(
        name = "User API",
        description = "Operations related to users"
)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(
            summary = "Get all users",
            description = "Returns all registered casino users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<List<UserResponse>> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @GetMapping("/users/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Returns a user by their unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){

        UserResponse user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/username/{username}")
    @Operation(
            summary = "Search users by username",
            description = "Returns all users with the specified username."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "404", description = "No users found")
    })
    public ResponseEntity<List<UserResponse>> getUserByUsername(
            @PathVariable String username) {

        List<UserResponse> users = userService.getUserByUsername(username);

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/country/{country}/status/{status}")
    @Operation(
            summary = "Search users by country and status",
            description = "Returns users filtered by country and account status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "404", description = "No users found")
    })
    public ResponseEntity<List<UserResponse>> getUsersByCountryAndStatus(
            @PathVariable String country,
            @PathVariable UserStatus status) {

        List<UserResponse> users = userService.getUserByCountryAndStatus(country, status);

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    @Operation(
            summary = "Create user",
            description = "Creates a new casino user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "409", description = "Member ID already exists")
    })
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request){

        UserResponse user = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/users/{id}")
    @Operation(
            summary = "Update user",
            description = "Updates an existing casino user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {

        UserResponse user = userService.updateUser(id, request);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    @Operation(
            summary = "Delete user",
            description = "Deletes the specified casino user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}

