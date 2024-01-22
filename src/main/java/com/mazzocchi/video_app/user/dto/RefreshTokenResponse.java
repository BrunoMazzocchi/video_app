package com.mazzocchi.video_app.user.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class RefreshTokenResponse {
    private String accessToken;
}
