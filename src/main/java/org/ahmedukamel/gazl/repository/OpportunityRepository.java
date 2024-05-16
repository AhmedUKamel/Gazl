package org.ahmedukamel.gazl.repository;

import org.ahmedukamel.gazl.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    @Query(value = """
            SELECT o
            FROM Opportunity o
            ORDER BY o.id
            LIMIT :limit
            OFFSET :offset
            """)
    List<Opportunity> selectOpportunitiesWithPagination(@Param(value = "limit") long limit,
                                                        @Param(value = "offset") long offset);
}