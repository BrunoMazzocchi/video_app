package com.mazzocchi.video_app.user.service;

import com.mazzocchi.video_app.user.service.domain.*;
import com.mazzocchi.video_app.user.service.repository.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Create a UserDetails object based on your User entity
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles("USER") // Set user roles/authorities here
                .build();
    }

    // Get id
    public Long getUserId(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user.getId();
    }
}