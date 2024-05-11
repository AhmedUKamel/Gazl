package org.ahmedukamel.gazl.repository;

import org.ahmedukamel.gazl.model.Charity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharityRepository extends JpaRepository<Charity, Integer> {
}