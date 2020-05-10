package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.controller.endpoint.ReportEndpoint;
import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import com.egemsoft.rickandmorty.service.ReportEndpointService;
import com.egemsoft.rickandmorty.swagger.GenerateSwaggerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@GenerateSwaggerClient
@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportEndpointService reportEndpointService;

    @GetMapping(value = ReportEndpoint.GET_REPORT)
    public GenericResponse getReport(Pageable pageable) {
        return reportEndpointService.getAllReport(pageable);
    }
}
