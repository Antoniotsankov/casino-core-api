package org.example.casinocoreapi.service;
import org.example.casinocoreapi.dto.CreateUserRequest;
import org.example.casinocoreapi.dto.UpdateUserRequest;
import org.example.casinocoreapi.dto.UserResponse;
import org.example.casinocoreapi.enums.UserStatus;
import org.example.casinocoreapi.exception.MemberIdAlreadyExistsException;
import org.example.casinocoreapi.exception.UserNotFoundException;
import org.example.casinocoreapi.model.User;
import org.example.casinocoreapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private UserService userService;

    private static final Long USER_ID = 1L;
    private static final String MEMBER_ID = "MEM-1001";
    private static final String USERNAME = "Antonio";
    private static final String COUNTRY = "Bulgaria";
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(USER_ID);
        user.setMemberId(MEMBER_ID);
        user.setUsername(USERNAME);
        user.setCountry(COUNTRY);
        user.setStatus(UserStatus.ACTIVE);
    }

    @Test
    void createUser_shouldCreateUserSuccessfully() {
        // Arrange - create a request as if it was sent from the client
        CreateUserRequest request = new CreateUserRequest();
        request.setMemberId(MEMBER_ID);
        request.setUsername(USERNAME);
        request.setCountry(COUNTRY);
        // Mock - there is no existing user with this Member ID
        when(userRepository.existsByMemberId(MEMBER_ID))
                .thenReturn(false);
        // Mock - when save() is called, return our prepared User object
        when(userRepository.save(any(User.class)))
                .thenReturn(user);
        // Act - execute the method we are testing
        UserResponse response = userService.createUser(request);
        // Assert - verify that the returned data is correct
        assertEquals(USERNAME, response.getUsername());
        assertEquals(MEMBER_ID, response.getMemberId());
        assertEquals(COUNTRY, response.getCountry());
        // Verify - check that the User was saved
        verify(userRepository).save(any(User.class));
        // Verify - check that a Wallet was created for the new User
        verify(walletService).createWallet(user);
    }

    @Test
    void createUser_shouldThrowException_WhenMemberIdAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest();
        request.setMemberId(MEMBER_ID);
        request.setUsername(USERNAME);
        request.setCountry(COUNTRY);

        when(userRepository.existsByMemberId(MEMBER_ID)).thenReturn(true);
        // Act & Assert
        MemberIdAlreadyExistsException exception = assertThrows(
                MemberIdAlreadyExistsException.class,
                () -> userService.createUser(request)
        );

        assertEquals(
                "Member ID already exists",
                exception.getMessage()
        );
    }

    @Test
    void getUserById_shouldReturnUser() {
        // Arrange - prepare the repository to return our test User
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));
        // Act - execute the method
        UserResponse response = userService.getUserById(USER_ID);
        // Assert - verify the returned User data
        assertEquals(USER_ID, response.getId());
        assertEquals(MEMBER_ID, response.getMemberId());
        assertEquals(USERNAME, response.getUsername());
        assertEquals(COUNTRY, response.getCountry());
    }

    @Test
    void getUserById_shouldThrowException_WhenUserNotFound() {
        // Arrange - telling mock repository, there is no user with this ID
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());
        // Act & Assert
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(USER_ID)
        );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }

    @Test
    void getUser_shouldReturnAllUsers() {
        when(userRepository.findAll())
                .thenReturn(List.of(user));

        List<UserResponse> response = userService.getUser();
        // Assert - verify that one UserResponse is returned
        assertEquals(1, response.size());
        // Assert - verify that the returned data is correct
        assertEquals(USER_ID, response.get(0).getId());
        assertEquals(MEMBER_ID, response.get(0).getMemberId());
        assertEquals(USERNAME, response.get(0).getUsername());
        assertEquals(COUNTRY, response.get(0).getCountry());
        //Verify - check that findAll() was called
        verify(userRepository).findAll();
    }

    @Test
    void getUserByUsername_shouldReturnUsers() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(List.of(user));

        List<UserResponse> response = userService.getUserByUsername(USERNAME);

        assertEquals(1, response.size());
        assertEquals(USER_ID, response.get(0).getId());
        assertEquals(MEMBER_ID, response.get(0).getMemberId());
        assertEquals(USERNAME, response.get(0).getUsername());
        assertEquals(COUNTRY, response.get(0).getCountry());

        verify(userRepository).findByUsername(USERNAME);
    }

    @Test
    void getUserByCountryAndStatus_shouldReturnUsers() {
        when(userRepository.findByCountryAndStatus(COUNTRY, UserStatus.ACTIVE))
                .thenReturn(List.of(user));

        List<UserResponse> response =
                userService.getUserByCountryAndStatus(COUNTRY, UserStatus.ACTIVE);

        assertEquals(1, response.size());
        assertEquals(USERNAME, response.get(0).getUsername());
        assertEquals(MEMBER_ID, response.get(0).getMemberId());
        assertEquals(COUNTRY, response.get(0).getCountry());

        verify(userRepository)
                .findByCountryAndStatus(COUNTRY, UserStatus.ACTIVE);
    }

    @Test
    void updateUser_shouldUpdateUserSuccessfully() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername("Peter");
        request.setCountry("Germany");
        request.setStatus(UserStatus.BLOCKED);

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));

        when(userRepository.save(user))
                .thenReturn(user);

        UserResponse response =
                userService.updateUser(USER_ID, request);

        assertEquals("Peter", response.getUsername());
        assertEquals("Germany", response.getCountry());
        assertEquals(UserStatus.BLOCKED, response.getStatus());

        verify(userRepository).save(user);
    }

    @Test
    void updateUser_shouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        UpdateUserRequest request = new UpdateUserRequest();

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.updateUser(USER_ID, request)
        );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }

    @Test
    void deleteUser_shouldDeleteUserSuccessfully() {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));

        userService.deleteUser(USER_ID);

        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_shouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.deleteUser(USER_ID)
        );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }
}