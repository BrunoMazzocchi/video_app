package com.mazzocchi.video_app.multimedia.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MultimediaDto {
    private Long id;
    private String name;
    private String contentType;
    private byte[] data;
}
