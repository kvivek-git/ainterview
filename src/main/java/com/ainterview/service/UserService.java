package com.ainterview.service;

import com.ainterview.dto.CreateUserRequest;
import com.ainterview.dto.UserResponse;
import com.ainterview.exception.DuplicateResourceException;
import com.ainterview.exception.ResourceNotFoundException;
import com.ainterview.model.Role;
import com.ainterview.model.User;
import com.ainterview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request){
        if(userRepository.findByEmail(request.email()).isPresent()){
            throw new DuplicateResourceException("User with email " + request.email() + "already exists");
        }
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .updatedAt(LocalDateTime.now())
                .build();

        return UserResponse.from(userRepository.save(user));
    }

    public UserResponse getUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
        return UserResponse.from(user);
    }

    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return UserResponse.from(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User", id);
        }
        userRepository.deleteById(id);
    }
}
