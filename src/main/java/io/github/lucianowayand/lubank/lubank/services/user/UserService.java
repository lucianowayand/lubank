package io.github.lucianowayand.lubank.lubank.services.user;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import io.github.lucianowayand.lubank.lubank.models.user.LoginDto;
import io.github.lucianowayand.lubank.lubank.models.user.User;
import io.github.lucianowayand.lubank.lubank.models.user.CreateUserDto;
import io.github.lucianowayand.lubank.lubank.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    public UserService(UserRepository repository, @Lazy AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
    }

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    @Autowired
    private final UserRepository repository;

    private final AuthenticationManager authenticationManager;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(UUID id) {
        return repository.findById(id);
    }

    public User findByGovRegCode(String govRegCode) {
        return repository.findByGovRegCode(govRegCode);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByGovRegCode(username);

        if(user != null) {
            return org.springframework.security.core.userdetails.User.withUsername(user.getGovRegCode()).password(user.getPassword()).build();
        }

        return null;
    }

    private String createJwtToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claim = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24 * 3600))
                .subject(user.getGovRegCode())
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claim);

        return encoder.encode(params).getTokenValue();
    }

    public ResponseEntity<Object> createUser(CreateUserDto dto) {
        try {
            var user = repository.findByGovRegCode(dto.getGovRegCode());
            if (user != null){
                return ResponseEntity.badRequest().body("A user with this government registration code already exists.");
            }

            user = repository.findByEmail(dto.getEmail());
            if (user != null){
                return ResponseEntity.badRequest().body("A user with this email already exists.");
            }

            var bCryptEncoder = new BCryptPasswordEncoder();
            User newUser = new User();
            newUser.setName(dto.getName());
            newUser.setEmail(dto.getEmail());
            newUser.setGovRegCode(dto.getGovRegCode());
            newUser.setPassword(bCryptEncoder.encode(dto.getPassword()));
            newUser.setCreatedAt(new Date());

            int accountNumber =  ThreadLocalRandom.current().nextInt(1, 100000);
            newUser.setAccount(accountNumber);

            repository.save(newUser);

            String jwtToken = createJwtToken(newUser);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", newUser);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            System.out.println("Exception :");
            ex.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Internal Server Error.");
    }

    public ResponseEntity<Object> authenticateUser(LoginDto data){
        try {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                data.getGovRegCode(),
                                data.getPassword()
                        )
                );
            } catch (Exception err) {
                return ResponseEntity.status(401).body("Government Registration Code and password don't match.");
            }

            User user = repository.findByGovRegCode(data.getGovRegCode());
            String jwtToken = createJwtToken(user);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", user);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            System.out.println("Exception :");
            ex.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Internal Server Error.");
    }

}
