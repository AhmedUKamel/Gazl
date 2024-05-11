package org.ahmedukamel.gazl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;
import org.ahmedukamel.gazl.model.enumration.Gender;
import org.ahmedukamel.gazl.model.enumration.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "USER_EMAIL_UNIQUE_CONSTRAINT", columnNames = "email"),
        @UniqueConstraint(name = "USER_PHONE_NUMBER_UNIQUE_CONSTRAINT", columnNames = {"code", "number"})
})
public class User implements UserDetails {
    @Id
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String picture;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "countryCode", column = @Column(name = "code", nullable = false)),
            @AttributeOverride(name = "nationalNumber", column = @Column(name = "number", nullable = false))
    })
    private PhoneNumber phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(16) default 'VISITOR'")
    private Role role = Role.VISITOR;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime registration;

    @ManyToOne
    private Charity charity;

    @ManyToOne
    private Government government;

    @Column(nullable = false, columnDefinition = "bit(1) default true")
    private boolean accountNonLocked = true;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean enabled;

    @OneToMany(mappedBy = "business")
    private Set<Opportunity> opportunities = new HashSet<>();

    @OneToMany(mappedBy = "business")
    private Set<Case> cases = new HashSet<>();

    @OneToMany(mappedBy = "visitor")
    private Set<Complain> complains = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(this.role);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}