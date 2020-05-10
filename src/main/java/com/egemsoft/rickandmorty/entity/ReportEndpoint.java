package com.egemsoft.rickandmorty.entity;


import com.egemsoft.rickandmorty.entity.base.BaseAuditEntity;
import com.egemsoft.rickandmorty.enums.EndpointEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "report_endpoint")
public class ReportEndpoint extends BaseAuditEntity {

    private Date createDate;
    private String host;
    private String remoteAddress;
    private String name;
    private String method;

    @Column(columnDefinition = "text")
    private String requestBody;

    @Enumerated(EnumType.STRING)
    private EndpointEnum type;

    @Column(columnDefinition = "text")
    private String requestHeader;

    @Column(columnDefinition = "text")
    private String responseBody;

    @Column(columnDefinition = "text")
    private String responseHeader;
}
