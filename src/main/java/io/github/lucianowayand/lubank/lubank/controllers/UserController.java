package io.github.lucianowayand.lubank.lubank.controllers;

import io.github.lucianowayand.lubank.lubank.domain.User;
import io.github.lucianowayand.lubank.lubank.domain.user.UserDTO;
import io.github.lucianowayand.lubank.lubank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    private User createUser(@RequestBody UserDTO data){
        return service.createUser(data);
    }

    @RequestMapping
    private List<User> getUsers(){
        return service.findAll();
    }

    @RequestMapping("/{id}")
    private User findById(@PathVariable("id") UUID id){
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    private void deleteUser(@PathVariable("id") UUID id){
        service.deleteById(id);
    }
}
