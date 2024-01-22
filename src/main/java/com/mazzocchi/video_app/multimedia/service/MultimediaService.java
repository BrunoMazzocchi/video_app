package com.mazzocchi.video_app.multimedia.service;

import com.mazzocchi.video_app.multimedia.dto.*;
import com.mazzocchi.video_app.multimedia.service.domain.*;
import com.mazzocchi.video_app.multimedia.service.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class MultimediaService {

    private final MultimediaEntityRepository multimediaEntityRepository;

    @Autowired
    public MultimediaService(MultimediaEntityRepository multimediaEntityRepository) {
        this.multimediaEntityRepository = multimediaEntityRepository;
    }

    public MultimediaDto getMultimediaById(Long id) {
        MultimediaEntity multimediaEntity = multimediaEntityRepository.findById(id).orElse(null);
        if (multimediaEntity == null) {
            return null;
        }

        return multimediaDtoFromEntity(multimediaEntity);
    }

    public MultimediaDto saveMultimedia(MultimediaDto multimediaDto) {
        MultimediaEntity multimediaEntity = new MultimediaEntity();
        multimediaEntity.setName(multimediaDto.getName());
        multimediaEntity.setContentType(multimediaDto.getContentType());
        multimediaEntity.setData(multimediaDto.getData());


        multimediaEntity = multimediaEntityRepository.save(multimediaEntity);

        return multimediaDtoFromEntity(multimediaEntity);
    }


    MultimediaDto multimediaDtoFromEntity (MultimediaEntity multimediaEntity) {
        MultimediaDto multimediaDto = new MultimediaDto();
        multimediaDto.setId(multimediaEntity.getId());
        multimediaDto.setName(multimediaEntity.getName());
        multimediaDto.setContentType(multimediaEntity.getContentType());
        multimediaDto.setData(multimediaEntity.getData());

        return multimediaDto;
    }
}
