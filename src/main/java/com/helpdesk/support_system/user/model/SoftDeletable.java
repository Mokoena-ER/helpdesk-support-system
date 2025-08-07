package com.helpdesk.support_system.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class SoftDeletable {

    @Column(name = "deleted", nullable = false)
    protected boolean deleted = false;

}
