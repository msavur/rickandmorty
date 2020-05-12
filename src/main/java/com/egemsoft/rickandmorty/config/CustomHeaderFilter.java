package com.egemsoft.rickandmorty.config;

import com.egemsoft.core.entity.ReportEndpoint;
import com.egemsoft.rickandmorty.convert.impl.LogReportEndpointConverter;
import com.egemsoft.rickandmorty.repository.ReportEndpointRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
@Configuration
@RequiredArgsConstructor
public class CustomHeaderFilter implements Filter {

    private final ReportEndpointRepository reportEndpointRepository;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        String servletPath = ((RequestFacade) request).getServletPath();
        Map<String, String> responseInfo = getHeadersInfo(request);
        String requestHeader = new Gson().toJson(responseInfo);
        try {
            filterChain.doFilter(wrappedRequest, response);
            if (!servletPath.equals("/report")) {
                LogReportEndpointConverter reportEndpointConverter = new LogReportEndpointConverter();
                ReportEndpoint reportEndpoint = reportEndpointConverter.convert(wrappedRequest);
                reportEndpoint.setRequestHeader(requestHeader);
                reportEndpointRepository.save(reportEndpoint);
            }
        } catch (Exception e) {
            log.trace("exception trace: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }


}
