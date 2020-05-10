package com.egemsoft.rickandmorty.entity;


import com.egemsoft.rickandmorty.entity.base.BaseAuditEntity;
import com.egemsoft.rickandmorty.enums.SourceTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "image")
public class Image extends BaseAuditEntity {

    @Enumerated(EnumType.STRING)
    private SourceTypeEnum sourceType;

    @ManyToOne
    private Character character;

}
