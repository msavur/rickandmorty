package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.core.entity.ReportEndpoint;
import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.model.response.ReportEndpointResponse;
import com.google.gson.Gson;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            reportEndpointResponse.setCreateDate(reportEndpoint.getCreateDate());
            reportEndpointResponse.setHeader(new Gson().fromJson(reportEndpoint.getRequestHeader(), Map.class));
            reportEndpointResponse.setRemoteAddress(reportEndpoint.getRemoteAddress());
            reportEndpointResponse.setType(reportEndpoint.getType());
            reportEndpointResponse.setRequestBody(reportEndpoint.getRequestBody());
            reportEndpointResponse.setMethod(reportEndpoint.getMethod());
            reportEndpointResponse.setHost(reportEndpoint.getHost());
            endpointResponses.add(reportEndpointResponse);
        });

        return endpointResponses;
    }
}
