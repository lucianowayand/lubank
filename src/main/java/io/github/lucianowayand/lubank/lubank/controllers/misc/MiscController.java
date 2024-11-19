package io.github.lucianowayand.lubank.lubank.controllers.misc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MiscController {

    @GetMapping("/service-status")
    private ResponseEntity<Object> cronRefresher() {
        return ResponseEntity.ok("OK");
    }
}
