package io.github.lucianowayand.lubank.lubank.repo;

import io.github.lucianowayand.lubank.lubank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepo extends JpaRepository<User, Integer> {
}
