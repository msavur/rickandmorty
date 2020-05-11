package com.egemsoft.rickandmorty.entity;


import com.egemsoft.rickandmorty.entity.base.BaseAuditEntity;
import com.egemsoft.rickandmorty.enums.CharacterStatusEnum;
import com.egemsoft.rickandmorty.enums.GenderEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "character")
public class Character extends BaseAuditEntity {

    private Long remoteId;

    @Enumerated(EnumType.STRING)
    private CharacterStatusEnum status;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @ManyToOne
    private Kind kind;

    @ManyToOne(cascade = CascadeType.ALL)
    private CharacterType type;

    @ManyToOne
    private Location location;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
    private List<Image> images;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "character_episodes",
            joinColumns = @JoinColumn(name = "character_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "episode_id", referencedColumnName = "id")
    )
    private Set<Episode> episodes = new HashSet<>();
}
