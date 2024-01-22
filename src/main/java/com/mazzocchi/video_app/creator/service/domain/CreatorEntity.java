package com.mazzocchi.video_app.creator.service.domain;

import com.mazzocchi.video_app.user.service.domain.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "creator")
@AllArgsConstructor
@NoArgsConstructor
public class CreatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Other fields specific to the "creator" entity

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

}
