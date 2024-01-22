package com.mazzocchi.video_app.user.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class RefreshTokenRequest {
    private String refreshToken;
}
