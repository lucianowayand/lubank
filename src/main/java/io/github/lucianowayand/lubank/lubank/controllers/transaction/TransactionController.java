package io.github.lucianowayand.lubank.lubank.controllers.transaction;

import io.github.lucianowayand.lubank.lubank.models.user.User;
import io.github.lucianowayand.lubank.lubank.services.transaction.TransactionService;
import io.github.lucianowayand.lubank.lubank.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getUserTransactions(Authentication auth){
        User user = userService.findByGovRegCode(auth.getName());

        return ResponseEntity.ok(service.findByUserId(user.getId()));
    }

    @GetMapping("/balance")
    public ResponseEntity<Object> getUserBalance(Authentication auth){
        User user = userService.findByGovRegCode(auth.getName());

        return ResponseEntity.ok(service.getUserBalance(user.getId()));
    }
}
