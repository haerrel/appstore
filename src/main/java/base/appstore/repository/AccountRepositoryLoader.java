package base.appstore.repository;

import base.appstore.model.Account;
import base.appstore.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountRepositoryLoader implements ApplicationListener<ApplicationReadyEvent> {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountRepositoryLoader(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.accountRepository.deleteAll();
        Account account = new Account();
        account.setFirstname("Demo");
        account.setLastname("Account");
        account.setUsername("DemoAccount");
        account.setRole(Role.DEVELOPER);
        account.setPassword(passwordEncoder.encode("DemoPassword"));
        this.accountRepository.save(account);
    }
}
