package io.github.lucianowayand.lubank.lubank.controllers.transaction;

import io.github.lucianowayand.lubank.lubank.models.transaction.CreateTransactionDto;
import io.github.lucianowayand.lubank.lubank.models.user.User;
import io.github.lucianowayand.lubank.lubank.services.transaction.TransactionService;
import io.github.lucianowayand.lubank.lubank.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @PostMapping
    public ResponseEntity<Object> sendTransaction(Authentication auth, @Valid @RequestBody CreateTransactionDto dto, BindingResult result){
        if(result.hasErrors()){
            var errorsList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for(int i = 0; i < errorsList.size(); i++){
                var error = (FieldError) errorsList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());

                return ResponseEntity.badRequest().body(errorsMap);
            }
        }


        try {
            User user = userService.findByGovRegCode(auth.getName());
            User receiver = userService.findById(dto.getReceiverId());

            Float balance = service.getUserBalance(user.getId());

            if (receiver != null && balance >= dto.getAmount()){
                service.sendTransaction(user.getId(), dto);
                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.badRequest().body("Remetente não existente ou saldo insuficiente.");
            }

        } catch (Exception e) {
            System.out.println("Exception :");
            e.printStackTrace();

            return ResponseEntity.badRequest().body("Internal Server Error.");
        }
    }
}
