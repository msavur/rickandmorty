package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.ReportEndpoint;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class LogReportEndpointConverter implements BaseConverter<ContentCachingRequestWrapper, ReportEndpoint> {

    @Override
    public ReportEndpoint convert(ContentCachingRequestWrapper request) {
        ReportEndpoint reportEndpoint = new ReportEndpoint();
        reportEndpoint.setRequestBody(new String(request.getContentAsByteArray()));
        reportEndpoint.setHost(request.getRemoteHost());
        reportEndpoint.setRemoteAddress(request.getRemoteAddr());
        reportEndpoint.setMethod(request.getMethod());
        reportEndpoint.setName(request.getRequestURI());
        reportEndpoint.setUrl(request.getRequestURI());
        reportEndpoint.setCreateDate(new Date());
        return reportEndpoint;
    }
}
