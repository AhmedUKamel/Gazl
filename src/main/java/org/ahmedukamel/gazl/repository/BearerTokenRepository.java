package org.ahmedukamel.gazl.repository;

import jakarta.transaction.Transactional;
import org.ahmedukamel.gazl.model.BearerToken;
import org.ahmedukamel.gazl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BearerTokenRepository extends JpaRepository<BearerToken, String> {
    Optional<BearerToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE BearerToken t
            SET t.revoked = true
            WHERE t.user = :user""")
    void revokeTokens(@Param(value = "user") User user);
}