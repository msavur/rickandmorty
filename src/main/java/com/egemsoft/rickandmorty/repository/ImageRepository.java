package com.egemsoft.rickandmorty.repository;

import com.egemsoft.core.entity.Image;
import com.egemsoft.core.enums.SourceTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllBySourceType(SourceTypeEnum sourceType);

    @Modifying
    @Transactional
    @Query("DELETE from Image i WHERE i.id in (:imageIds)")
    void deleteImageByIds(@Param("imageIds") List<Long> imageIds);
}
