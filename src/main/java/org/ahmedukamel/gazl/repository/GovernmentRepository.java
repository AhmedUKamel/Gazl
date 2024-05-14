package org.ahmedukamel.gazl.repository;

import org.ahmedukamel.gazl.model.Government;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GovernmentRepository extends JpaRepository<Government, Integer> {
    boolean existsByName(String name);

    @Query(value = """
            SELECT g
            FROM Government g
            ORDER BY g.id
            LIMIT :limit
            OFFSET :offset
            """)
    List<Government> selectGovernmentWithPagination(@Param(value = "limit") long limit,
                                                    @Param(value = "offset") long offset);
}