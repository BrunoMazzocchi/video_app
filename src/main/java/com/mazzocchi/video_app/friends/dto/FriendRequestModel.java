package com.mazzocchi.video_app.friends.dto;

import com.fasterxml.jackson.annotation.*;
import com.mazzocchi.video_app.friends.service.domain.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestModel {
    private Long id;

    private long senderId;
    private long receiverId;

    private FriendshipStatus status;
}
