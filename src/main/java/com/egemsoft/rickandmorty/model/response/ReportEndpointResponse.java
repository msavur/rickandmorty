package com.egemsoft.rickandmorty.model.response;


import com.egemsoft.core.enums.EndpointEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ReportEndpointResponse {
    private Long id;
    private String name;
    private String url;
    private Date created;
    private Map header;
    private Date createDate;
    private String host;
    private String remoteAddress;
    private String method;
    private String requestBody;
    private EndpointEnum type;
    private String requestHeader;
    private String responseBody;
    private String responseHeader;
}
