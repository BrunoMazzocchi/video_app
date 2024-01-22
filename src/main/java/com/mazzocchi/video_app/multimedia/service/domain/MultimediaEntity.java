package com.mazzocchi.video_app.multimedia.service.domain;

import com.mazzocchi.video_app.user.service.domain.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "multimedia")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MultimediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contentType;

    @Lob
    @Column(length = 1048576)
    private byte[] data;

    // Sender
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;
}
