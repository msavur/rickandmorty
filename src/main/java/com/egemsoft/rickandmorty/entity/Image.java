package com.egemsoft.rickandmorty.entity;


import com.egemsoft.rickandmorty.entity.base.BaseAuditEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "image")
public class Image extends BaseAuditEntity {

    private String sourceType;

    @ManyToOne
    private Character character;

}
