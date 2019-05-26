package base.appstore.repository;

import base.appstore.model.Account;
import base.appstore.model.PredefinedUser;
import base.appstore.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountRepositoryLoader implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AccountRepositoryLoader(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.accountRepository.deleteAll();
        addDemoUser();
        addDemoDeveloper();
        addDemoAdmin();
    }

    private void addDemoUser(){
        Account account = new Account();
        account.setFirstname("Demo");
        account.setLastname("User");
        account.setUsername(PredefinedUser.USER.getUsername());
        account.setRole(Role.USER);
        account.setPassword(passwordEncoder.encode(PredefinedUser.USER.getPassword()));
        this.accountRepository.save(account);
    }

    private void addDemoDeveloper(){
        Account account = new Account();
        account.setFirstname("Demo");
        account.setLastname("Developer");
        account.setUsername(PredefinedUser.DEVELOPER.getUsername());
        account.setRole(Role.DEVELOPER);
        account.setPassword(passwordEncoder.encode(PredefinedUser.DEVELOPER.getPassword()));
        this.accountRepository.save(account);
    }

    private void addDemoAdmin(){
        Account account = new Account();
        account.setFirstname("Demo");
        account.setLastname("Admin");
        account.setUsername(PredefinedUser.ADMIN.getUsername());
        account.setRole(Role.ADMIN);
        account.setPassword(passwordEncoder.encode(PredefinedUser.ADMIN.getPassword()));
        this.accountRepository.save(account);
    }
}
