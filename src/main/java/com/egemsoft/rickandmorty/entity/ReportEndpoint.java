package com.egemsoft.rickandmorty.entity;


import com.egemsoft.rickandmorty.entity.base.BaseAuditEntity;
import com.egemsoft.rickandmorty.enums.EndpointEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "report_endpoint")
public class ReportEndpoint extends BaseAuditEntity {

    private String name;

    private String header;

    private String body;

    private String method;

    @Enumerated(EnumType.STRING)
    private EndpointEnum type;

}
