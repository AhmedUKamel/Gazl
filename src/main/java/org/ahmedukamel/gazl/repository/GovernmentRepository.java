package org.ahmedukamel.gazl.repository;

import org.ahmedukamel.gazl.model.Government;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentRepository extends JpaRepository<Government, Integer> {
}