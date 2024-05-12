package org.ahmedukamel.gazl.repository;

import org.ahmedukamel.gazl.model.AccountActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountActivationTokenRepository extends JpaRepository<AccountActivationToken, UUID> {
}