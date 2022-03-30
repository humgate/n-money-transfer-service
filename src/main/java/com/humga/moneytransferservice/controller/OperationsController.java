package com.humga.moneytransferservice.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationsController {
    private final PasswordEncoder passwordEncoder;

    OperationsController(PasswordEncoder passwordEncoder) {this.passwordEncoder = passwordEncoder;}

    @GetMapping("/gethash")
    public String getHash(@RequestParam("password") String password) {
        return passwordEncoder.encode(password);
    }

    @GetMapping("/verifyhash")
    public boolean verifyHash(@RequestParam("password") String password, @RequestParam("hash") String encoded ) {
        return passwordEncoder.matches(password, encoded);
    }



}
