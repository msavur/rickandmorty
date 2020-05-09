package com.egemsoft.rickandmorty.model.response;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReportEndpointResponse {
    private Long id;
    private String name;
    private String url;
    private Date created;
    private String body;
    private String header;
}
