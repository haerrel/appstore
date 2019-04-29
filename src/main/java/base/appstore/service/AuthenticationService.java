package base.appstore.service;


import base.appstore.exception.EntityNotFoundException;
import base.appstore.model.JWTTokenResponse;
import base.appstore.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private AccountRepository accountRepository;
    private JwtTokenService jwtTokenService;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(AccountRepository accountRepository, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public JWTTokenResponse generateJWTToken(String username, String password) {
        return accountRepository.findOneByUsername(username)
                .filter(account -> passwordEncoder.matches(password, account.getPassword()))
                .map(account -> new JWTTokenResponse(jwtTokenService.generateToken(username)))
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }
}
