package org.ahmedukamel.gazl.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.gazl.model.embeddable.Location;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;
import org.ahmedukamel.gazl.model.enumration.CaseHelperType;
import org.ahmedukamel.gazl.model.enumration.CaseStatus;

@Setter
@Getter
@Entity
@Table(name = "cases", uniqueConstraints = {
        @UniqueConstraint(name = "CASE_PHONE_NUMBER_UNIQUE_CONSTRAINT", columnNames = {"code", "number"}),
        @UniqueConstraint(name = "CASE_LOCATION_UNIQUE_CONSTRAINT", columnNames = {"latitude", "longitude"})
})
public class Case {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "latitude", column = @Column(nullable = false)),
            @AttributeOverride(name = "longitude", column = @Column(nullable = false))
    })
    private Location location;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "countryCode", column = @Column(name = "code", nullable = false)),
            @AttributeOverride(name = "nationalNumber", column = @Column(name = "number", nullable = false))
    })
    private PhoneNumber phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(16) default 'TODO'")
    private CaseStatus status = CaseStatus.TODO;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private CaseHelperType helperType;

    @OneToOne(cascade = CascadeType.ALL)
    private Guest guest;

    @ManyToOne
    private User business;

    @ManyToOne
    private Charity charity;

    @ManyToOne
    private Government government;
}