package base.appstore.service;


import base.appstore.exception.EntityNotFoundException;
import base.appstore.model.Account;
import base.appstore.model.JWTTokenResponse;
import base.appstore.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(AccountRepository accountRepository, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public JWTTokenResponse generateJWTToken(String username, String password) {
        Optional<Account> userAccount = accountRepository.findOneByUsername(username);

        return userAccount.filter(account -> passwordEncoder.matches(password, account.getPassword()))
                .map(account -> new JWTTokenResponse(
                        jwtTokenService.generateToken(userAccount.get())))
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }
}
