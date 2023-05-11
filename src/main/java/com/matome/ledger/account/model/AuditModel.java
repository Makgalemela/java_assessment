package com.matome.ledger.account.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdOn", "updatedOn"},
        allowGetters = true
)
@EnableJpaAuditing
@Getter
@Setter
public abstract class AuditModel implements Serializable {

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_on", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
