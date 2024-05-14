package org.ahmedukamel.gazl.repository;

import org.ahmedukamel.gazl.model.Charity;
import org.ahmedukamel.gazl.model.embeddable.Location;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharityRepository extends JpaRepository<Charity, Integer> {
    boolean existsByPhoneNumber(PhoneNumber phoneNumber);

    boolean existsByLocation(Location location);

    boolean existsByNameIgnoreCase(String name);

    @Query(value = """
            SELECT c
            FROM Charity c
            ORDER BY c.id
            LIMIT :limit
            OFFSET :offset""")
    List<Charity> selectCharitiesWithPagination(@Param(value = "limit") long limit,
                                                @Param(value = "offset") long offset);
}