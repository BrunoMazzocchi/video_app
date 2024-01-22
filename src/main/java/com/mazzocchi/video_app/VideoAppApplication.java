package com.mazzocchi.video_app;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Video App API", version = "1.0", description = "Documentation Video App API v1.0"))
public class VideoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoAppApplication.class, args);
    }

}
