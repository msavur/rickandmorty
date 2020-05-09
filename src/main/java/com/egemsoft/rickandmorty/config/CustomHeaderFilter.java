package com.egemsoft.rickandmorty.config;

import com.egemsoft.rickandmorty.convert.impl.LogReportEndpointConverter;
import com.egemsoft.rickandmorty.entity.ReportEndpoint;
import com.egemsoft.rickandmorty.repository.ReportEndpointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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
        System.out.println("Remote Host:" + request.getRemoteHost());
        System.out.println("Remote Address:" + request.getRemoteAddr());
        String servletPath = ((RequestFacade) request).getServletPath();
        System.out.println("servletPath :: " + servletPath);
        Enumeration<String> headerNames = ((RequestFacade) request).getHeaderNames();


        while (headerNames.hasMoreElements()) {
            System.out.println("header : " + headerNames.nextElement());
        }
        log.info("Logging Request  {} : {}", request.getMethod(), request.getRequestURI());

        LogReportEndpointConverter reportEndpointConverter = new LogReportEndpointConverter();
        ReportEndpoint reportEndpoint = reportEndpointConverter.convert(request);

        reportEndpointRepository.save(reportEndpoint);
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init filter");
    }
}
