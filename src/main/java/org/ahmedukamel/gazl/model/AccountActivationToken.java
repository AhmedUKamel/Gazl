package org.ahmedukamel.gazl.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.gazl.model.enumration.TokenType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "account_activation_tokens")
public class AccountActivationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private TokenType type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creation;

    @Column(nullable = false, updatable = false)
    private LocalDateTime expiration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean used;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;
}