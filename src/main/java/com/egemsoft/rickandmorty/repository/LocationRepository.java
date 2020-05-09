package com.egemsoft.rickandmorty.repository;

import com.egemsoft.rickandmorty.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
