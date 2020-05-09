package com.egemsoft.rickandmorty.repository;

import com.egemsoft.rickandmorty.entity.ReportEndpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportEndpointRepository extends JpaRepository<ReportEndpoint, Long> {
    Page<ReportEndpoint> findByOrderById(Pageable pageable);
}
