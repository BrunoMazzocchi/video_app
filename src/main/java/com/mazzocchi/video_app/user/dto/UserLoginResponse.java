package com.mazzocchi.video_app.user.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private Long id;
    private String username;
    private String email;

    private String accessToken;

    private String refreshToken;
}
