package com.egemsoft.rickandmorty.repository;

import com.egemsoft.core.entity.CharacterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterTypeRepository extends JpaRepository<CharacterType, Long> {
    List<CharacterType> findAll();
}
