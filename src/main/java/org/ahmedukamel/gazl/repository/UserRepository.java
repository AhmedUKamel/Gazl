package org.ahmedukamel.gazl.repository;

import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.model.embeddable.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhoneNumber(PhoneNumber phoneNumber);
}