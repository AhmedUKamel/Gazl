package org.ahmedukamel.gazl.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "governments", uniqueConstraints = {
        @UniqueConstraint(name = "GOVERNMENT_NAME_UNIQUE_CONSTRAINT", columnNames = "name")
})
public class Government {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String logo;

    @OneToMany(mappedBy = "government")
    private Set<User> employees = new HashSet<>();

    @OneToMany(mappedBy = "government")
    private Set<Opportunity> opportunities = new HashSet<>();

    @OneToMany(mappedBy = "government")
    private Set<Case> cases = new HashSet<>();
}