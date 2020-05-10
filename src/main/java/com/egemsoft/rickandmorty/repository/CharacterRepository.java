package com.egemsoft.rickandmorty.repository;

import com.egemsoft.rickandmorty.entity.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    Page<Character> findAll(Pageable pageable);

    Optional<Character> findById(Long id);

    List<Character> findAll();

    List<Character> findByRemoteIdIn(List<Long> remoteIds);

    @Query(value = "select name from Character ")
    List<String> getAllCharacterName();
}
