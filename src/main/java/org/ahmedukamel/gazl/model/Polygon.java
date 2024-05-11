package org.ahmedukamel.gazl.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.gazl.model.embeddable.Location;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "polygons")
public class Polygon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "polygon_points")
    private Set<Location> points = new HashSet<>();
}