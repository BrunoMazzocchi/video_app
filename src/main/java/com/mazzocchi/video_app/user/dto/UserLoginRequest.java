package com.mazzocchi.video_app.user.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {
    private String username;
    private String password;
    private String email;

}