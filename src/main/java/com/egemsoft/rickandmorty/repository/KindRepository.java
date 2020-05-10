package com.egemsoft.rickandmorty.repository;

import com.egemsoft.rickandmorty.entity.Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KindRepository extends JpaRepository<Kind, Long> {
    List<Kind> findAll();
}
