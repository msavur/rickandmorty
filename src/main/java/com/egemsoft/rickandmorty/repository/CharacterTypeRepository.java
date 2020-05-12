package com.egemsoft.rickandmorty.repository;

import com.egemsoft.core.entity.CharacterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CharacterTypeRepository extends JpaRepository<CharacterType, Long> {
    List<CharacterType> findAll();

    @Modifying
    @Transactional
    @Query("DELETE from CharacterType i WHERE i.id in (:characterTypeIds)")
    void deleteCharacterTypeIds(@Param("characterTypeIds") List<Long> characterTypeIds);
}
