package io.github.lucianowayand.lubank.lubank.controllers;

import io.github.lucianowayand.lubank.lubank.models.user.CreateUserDTO;
import io.github.lucianowayand.lubank.lubank.models.user.LoginDTO;
import io.github.lucianowayand.lubank.lubank.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserDTO data, BindingResult result) {
        if(result.hasErrors()){
            var errorsList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for(int i = 0; i < errorsList.size(); i++){
                var error = (FieldError) errorsList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());

                return ResponseEntity.badRequest().body(errorsMap);
            }
        }

        return service.createUser(data);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO data, BindingResult result){
        if(result.hasErrors()){
            var errorsList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for(int i = 0; i < errorsList.size(); i++){
                var error = (FieldError) errorsList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());

                return ResponseEntity.badRequest().body(errorsMap);
            }
        }

        return service.authenticateUser(data);
    }

}