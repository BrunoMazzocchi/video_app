package com.mazzocchi.video_app.friends.service.domain;

import com.mazzocchi.video_app.user.service.domain.*;
import jakarta.persistence.*;

import lombok.*;

import java.util.Date;

@Entity
@Table(name = "friends")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id_sender")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "user_id_receiver")
    private UserEntity receiver;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDateTime;

    public FriendsEntity(UserEntity sender, UserEntity receiver, FriendshipStatus status, Date requestDateTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.requestDateTime = requestDateTime;
    }
}
