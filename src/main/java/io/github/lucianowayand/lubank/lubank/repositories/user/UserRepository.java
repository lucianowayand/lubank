package io.github.lucianowayand.lubank.lubank.repositories.user;

import io.github.lucianowayand.lubank.lubank.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users WHERE id=?",nativeQuery = true)
    public User findById(UUID username);

    @Query(value = "SELECT * FROM users WHERE gov_reg_code=?", nativeQuery = true)
    public User findByGovRegCode(String govRegCode);

    @Query(value = "SELECT * FROM users WHERE email=?", nativeQuery = true)
    public User findByEmail(String email);
}
