package com.egemsoft.rickandmorty.service.impl;

import com.egemsoft.rickandmorty.convert.impl.ReportEndpointConverter;
import com.egemsoft.rickandmorty.convert.impl.endpoint.ApiEndpoint;
import com.egemsoft.rickandmorty.entity.ReportEndpoint;
import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import com.egemsoft.rickandmorty.model.generic.PageableInfo;
import com.egemsoft.rickandmorty.model.response.ReportEndpointResponse;
import com.egemsoft.rickandmorty.repository.ReportEndpointRepository;
import com.egemsoft.rickandmorty.service.ReportEndpointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportEndpointServiceImpl implements ReportEndpointService {

    private final ReportEndpointRepository reportEndpointRepository;

    @Override
    public GenericResponse<?> getAllReport(Pageable pageable) {
        Page<ReportEndpoint> reportEndpointPage = reportEndpointRepository.findByOrderById(pageable);
        ReportEndpointConverter reportEndpointConverter = new ReportEndpointConverter();
        List<ReportEndpointResponse> endpointResponses = reportEndpointConverter.convert(reportEndpointPage);
        PageableInfo pageableInfo = getPageableInfo(reportEndpointPage);
        return new GenericResponse(pageableInfo, endpointResponses);
    }

    private PageableInfo getPageableInfo(Page<ReportEndpoint> reportEndpoints) {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setCount(reportEndpoints.getTotalElements());
        pageableInfo.setNext(ApiEndpoint.PAGEABLE_EPISODE_URL + reportEndpoints.getNumber() + 1);
        pageableInfo.setPages(reportEndpoints.getSize());
        pageableInfo.setPrev("");
        return pageableInfo;
    }
}
