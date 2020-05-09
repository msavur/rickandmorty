package com.egemsoft.rickandmorty.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditEntity extends BaseEntity {

    protected String name;
    protected String url;
    protected Date created;

    @PrePersist
    void prePersist() {
    }

    @PreUpdate
    void preUpdate() {
    }

}
