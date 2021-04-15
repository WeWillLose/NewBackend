package com.diploma.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.Instant;

/**
 * Base abstract class for entities which will hold definitions for created, last modified, created by,
 * last modified by attributes.
 */
public abstract class AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name ="created_by")
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date")
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @Column(name = "last_modified_by")
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private Instant lastModifiedDate = Instant.now();

}
