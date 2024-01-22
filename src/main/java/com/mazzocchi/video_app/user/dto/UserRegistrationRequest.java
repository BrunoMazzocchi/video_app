package com.mazzocchi.video_app.user.dto;

import com.fasterxml.jackson.annotation.*;
import com.mazzocchi.video_app.util.*;
import jakarta.validation.constraints.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches

public class UserRegistrationRequest {
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String password;
    private String passwordConfirmation;
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

}