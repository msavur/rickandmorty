package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.ReportEndpoint;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class LogReportEndpointConverter implements BaseConverter<HttpServletRequest, ReportEndpoint> {

    @Override
    public ReportEndpoint convert(HttpServletRequest request) {
        ReportEndpoint reportEndpoint = new ReportEndpoint();
        reportEndpoint.setMethod(request.getMethod());
        reportEndpoint.setName(request.getRequestURI());
        reportEndpoint.setUrl(request.getRequestURI());
        reportEndpoint.setCreated(new Date());
        return reportEndpoint;
    }
}
