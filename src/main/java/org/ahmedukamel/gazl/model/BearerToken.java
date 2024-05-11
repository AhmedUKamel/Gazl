package org.ahmedukamel.gazl.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "bearer_tokens")
public class BearerToken {
    @Id
    @Column(length = 512)
    private String token;

    @Column(nullable = false)
    private Date expiration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}