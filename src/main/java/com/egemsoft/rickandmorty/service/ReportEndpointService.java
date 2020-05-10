package com.egemsoft.rickandmorty.service;


import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import org.springframework.data.domain.Pageable;

public interface ReportEndpointService {
    GenericResponse<?> getAllReport(Pageable pageable);

    GenericResponse<?> getThread();

}
