package com.mazzocchi.video_app.multimedia;

import com.mazzocchi.video_app.multimedia.dto.*;
import com.mazzocchi.video_app.multimedia.service.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;

@RestController
@RequestMapping("/api/multimedia")
public class MultimediaController {

    private final MultimediaService multimediaService;

    @Autowired
    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }


    @PostMapping("/save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully", content = @Content),
            @ApiResponse(responseCode = "500", description = "Failed to upload file"),
    })
    public ResponseEntity<String> uploadFile(
            @RequestParam("name") String name,
            @RequestParam("contentType") String contentType,
            @RequestParam("file") MultipartFile file) {
        System.out.println("File name: " + name);
        try {
            // Implement logic to save the multimedia content using multimediaService

            MultimediaDto multimediaDto = new MultimediaDto();

            multimediaDto.setName(name);
            multimediaDto.setContentType(contentType);
            multimediaDto.setData(file.getBytes());

            multimediaService.saveMultimedia(multimediaDto);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Multimedia found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Multimedia not found"),
    })
    public ResponseEntity<MultimediaDto> getMultimediaById(@PathVariable String id) {
        MultimediaDto multimedia = multimediaService.getMultimediaById(Long.valueOf(id));

        if (multimedia != null) {
            return new ResponseEntity<>(multimedia, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
