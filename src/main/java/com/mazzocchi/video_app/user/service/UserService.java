package com.mazzocchi.video_app.user.service;

import com.mazzocchi.video_app.security.*;
import com.mazzocchi.video_app.user.dto.*;
import com.mazzocchi.video_app.user.service.domain.*;
import com.mazzocchi.video_app.user.service.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void registerUser(UserRegistrationRequest request) {
        // Check if the username is already taken
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("Username is already taken");
        }

        String refreshToken = jwtTokenProvider.generateRefreshToken(request.getUsername());

        // Hash the password before storing it in the database
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Create a new userEntity
        UserEntity userEntity = new UserEntity(request.getUsername(), encodedPassword, request.getEmail(), refreshToken);



        // Save the userEntity to the database
        userRepository.save(userEntity);

        // Convert the UserEntity entity to UserModelResponse
        convertToUserModelResponse(userEntity);
    }

    public UserModelResponse getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return convertToUserModelResponse(userEntity);
        } else {
            return null;
        }
    }

    private UserModelResponse convertToUserModelResponse(UserEntity userEntity) {
        return new UserModelResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail());
    }

    public UserLoginResponse loginUser(UserLoginRequest request) {
        // Check if both username and email are null
        if (request.getUsername() == null && request.getEmail() == null) {
            throw new IllegalArgumentException("Both username and email are null");
        }

        // Find user by username or email
        UserEntity user = findByUsernameOrEmail(request.getUsername(), request.getEmail());

        // Validate user credentials
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        // Generate JWT
        String accessToken = jwtTokenProvider.generateAccessToken(user.getUsername());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());

        // Save refresh token in your user entity or a separate table
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new UserLoginResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                accessToken,
                refreshToken
        );
    }

    private UserEntity findByUsernameOrEmail(String username, String email) {
        if (username != null) {
            return userRepository.findByUsername(username);
        } else if (email != null) {
            return userRepository.findByEmail(email);
        }
        return null;
    }

    public RefreshTokenResponse refreshAccessToken(String refreshToken) {
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        UserEntity user = userRepository.findByUsername(username);
        Boolean refreshTokenEquals = user.getRefreshToken().equals(refreshToken);

        if (refreshTokenEquals) {
            // Generate a new access token
            String newAccessToken = jwtTokenProvider.generateAccessToken(username);
            RefreshTokenResponse response = new RefreshTokenResponse();
            response.setAccessToken(newAccessToken);
            // Return the new access token
            return response;
        }

        // Invalid refresh token
        throw new RuntimeException("Invalid refresh token");
    }
}