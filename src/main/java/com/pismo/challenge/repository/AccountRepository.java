package com.pismo.challenge.repository;

import com.pismo.challenge.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findFirstByDocumentNumber(final Long documentNumber);

}
