package io.github.lucianowayand.lubank.lubank.repositories;

import io.github.lucianowayand.lubank.lubank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users WHERE id=?",nativeQuery = true)
    public User findById(UUID username);
}
