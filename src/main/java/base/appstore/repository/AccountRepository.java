package base.appstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import base.appstore.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findOneByUsername(String username);
}

