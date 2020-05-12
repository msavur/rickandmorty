package com.egemsoft.rickandmorty.repository;

import com.egemsoft.core.entity.Episode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    Page<Episode> findByOrderById(Pageable pageable);

    Optional<Episode> findById(Long id);

    List<Episode> findAll();
}
