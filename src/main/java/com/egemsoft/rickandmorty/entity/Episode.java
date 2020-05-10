package com.egemsoft.rickandmorty.entity;


import com.egemsoft.rickandmorty.entity.base.BaseAuditEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "episode")
public class Episode extends BaseAuditEntity {

    private Long remoteId;

    @JsonProperty()
    private Date airDate;
    private String episode;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "episodes")
    private Set<Character> characters = new HashSet<>();
}
