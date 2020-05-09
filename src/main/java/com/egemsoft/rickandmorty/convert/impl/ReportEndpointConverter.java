package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.ReportEndpoint;
import com.egemsoft.rickandmorty.model.response.ReportEndpointResponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class ReportEndpointConverter implements BaseConverter<Page<ReportEndpoint>, List<ReportEndpointResponse>> {

    @Override
    public List<ReportEndpointResponse> convert(Page<ReportEndpoint> input) {

        List<ReportEndpointResponse> endpointResponses = new ArrayList<>();

        input.getContent().forEach(reportEndpoint -> {
            ReportEndpointResponse reportEndpointResponse = new ReportEndpointResponse();
            reportEndpointResponse.setId(reportEndpoint.getId());
            reportEndpointResponse.setCreated(reportEndpoint.getCreated());
            reportEndpointResponse.setName(reportEndpoint.getName());
            reportEndpointResponse.setUrl(reportEndpoint.getUrl());
            endpointResponses.add(reportEndpointResponse);
        });

        return endpointResponses;
    }
}
