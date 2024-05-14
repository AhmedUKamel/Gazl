package org.ahmedukamel.gazl.model;

import jakarta.persistence.*;
import lombok.*;
import org.ahmedukamel.gazl.model.embeddable.Location;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "charities", uniqueConstraints = {
        @UniqueConstraint(name = "CHARITY_NAME_UNIQUE_CONSTRAINT", columnNames = "name"),
        @UniqueConstraint(name = "CHARITY_PHONE_NUMBER_UNIQUE_CONSTRAINT", columnNames = {"code", "number"}),
        @UniqueConstraint(name = "CHARITY_LOCATION_UNIQUE_CONSTRAINT", columnNames = {"latitude", "longitude"})
})
public class Charity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "countryCode", column = @Column(name = "code", nullable = false)),
            @AttributeOverride(name = "nationalNumber", column = @Column(name = "number", nullable = false))
    })
    private PhoneNumber phoneNumber;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "latitude", column = @Column(nullable = false)),
            @AttributeOverride(name = "longitude", column = @Column(nullable = false))
    })
    private Location location;

    @OneToMany(mappedBy = "charity")
    private Set<User> employees = new HashSet<>();

    @OneToMany(mappedBy = "charity")
    private Set<Opportunity> opportunities = new HashSet<>();

    @OneToMany(mappedBy = "charity")
    private Set<Case> cases = new HashSet<>();
}