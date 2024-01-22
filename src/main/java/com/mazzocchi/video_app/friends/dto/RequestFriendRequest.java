package com.mazzocchi.video_app.friends.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class RequestFriendRequest {
    private long senderId;
    private long receiverId;
}
