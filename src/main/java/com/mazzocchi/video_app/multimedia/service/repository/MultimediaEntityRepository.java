package com.mazzocchi.video_app.multimedia.service.repository;

import com.mazzocchi.video_app.multimedia.service.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface MultimediaEntityRepository  extends JpaRepository<MultimediaEntity, Long> {

}
