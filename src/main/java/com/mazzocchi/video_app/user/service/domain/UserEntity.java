package com.mazzocchi.video_app.user.service.domain;

import com.mazzocchi.video_app.creator.service.domain.*;
import com.mazzocchi.video_app.friends.service.domain.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull(message = "First Name cannot be null")
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(name = "refresh_token")
    private String refreshToken;

    public UserEntity() {}

    public UserEntity(String username, String password, String email, String refreshToken) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.refreshToken = refreshToken;
    }

    // Relation with the "creator" entity
    @OneToOne(mappedBy = "user")
    private CreatorEntity creator;


    // Relation with the "friends" entity to handle the logic
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<FriendsEntity> sentFriendRequests;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<FriendsEntity> receivedFriendRequests;


    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<UserEntity> friends;

}