package io.github.lucianowayand.lubank.lubank.services;

import io.github.lucianowayand.lubank.lubank.domain.User;
import io.github.lucianowayand.lubank.lubank.domain.user.UserDTO;
import io.github.lucianowayand.lubank.lubank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User createUser(UserDTO dto){
        User newUser = new User();
        newUser.setEmail(dto.email());
        newUser.setName(dto.name());
        newUser.setGovRegCode(dto.govRegCode());

        return repository.save(newUser);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void deleteById(UUID id){
        var user = repository.findById(id);

        if (user != null) {
            repository.delete(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public User findById(UUID id) {
        return repository.findById(id);
    }
}
