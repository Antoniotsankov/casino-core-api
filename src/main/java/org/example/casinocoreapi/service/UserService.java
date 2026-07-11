package org.example.casinocoreapi.service;

import org.example.casinocoreapi.dto.CreateUserRequest;
import org.example.casinocoreapi.dto.UpdateUserRequest;
import org.example.casinocoreapi.dto.UserResponse;
import org.example.casinocoreapi.enums.UserStatus;
import org.example.casinocoreapi.exception.MemberIdAlreadyExistsException;
import org.example.casinocoreapi.exception.UserNotFoundException;
import org.example.casinocoreapi.model.User;
import org.springframework.stereotype.Service;
import org.example.casinocoreapi.repository.UserRepository;




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final WalletService walletService;

    public UserService(UserRepository userRepository, WalletService walletService){
        this.userRepository = userRepository;
        this.walletService = walletService;
    }

    public List<UserResponse> getUserByUsername(String username){

        List<User> users = userRepository.findByUsername(username);

        List<UserResponse> responses = new ArrayList<>();

        for (User user : users) {

            UserResponse response = buildUserResponse(user);
            responses.add(response);
        }
        return responses;
    }

    private UserResponse buildUserResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setMemberId(user.getMemberId());
        response.setUsername(user.getUsername());
        response.setCountry(user.getCountry());
        response.setStatus(user.getStatus());
        response.setCreatedAt(user.getCreatedAt());

        return response;

    }

    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return buildUserResponse(user);
    }

    public List<UserResponse> getUserByCountryAndStatus(String country, UserStatus status){

        List<User> users = userRepository.findByCountryAndStatus(country, status);

        List<UserResponse> responses = new ArrayList<>();

        for (User user : users) {

            UserResponse response = buildUserResponse(user);
            responses.add(response);
        }
        return responses;
    }

    public List<UserResponse> getUser() {

        List<User> users = userRepository.findAll();

        List<UserResponse> responses = new ArrayList<>();

        for (User user : users) {

            UserResponse response = buildUserResponse(user);
            responses.add(response);
        }
        return responses;


    }

    private User buildUser(
            String memberId,

            String username,
            String country,
            UserStatus status) {

        User user = new User();

        user.setMemberId(memberId);
        user.setUsername(username);
        user.setCountry(country);
        user.setStatus(status);
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

    public UserResponse createUser(CreateUserRequest request) {

        if(userRepository.existsByMemberId(request.getMemberId())) {
            throw new MemberIdAlreadyExistsException("Member ID already exists");
        }

        User user = buildUser(
                request.getMemberId(),
                request.getUsername(),
                request.getCountry(),
                UserStatus.ACTIVE
        );

        User savedUser = userRepository.save(user);
        walletService.createWallet(savedUser);

        return buildUserResponse(savedUser);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request){

        User user = userRepository.findById(id).orElse(null);

        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        user.setUsername(request.getUsername());
        user.setCountry(request.getCountry());
        user.setStatus(request.getStatus());

        User updatedUser = userRepository.save(user);

        return buildUserResponse(updatedUser);
    }

    public void deleteUser(Long id){

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        userRepository.delete(user);
    }


}