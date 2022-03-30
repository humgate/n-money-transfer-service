package com.humga.moneytransferservice.controller;

import com.humga.moneytransferservice.model.Card;
import com.humga.moneytransferservice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OperationsController {
    private final PasswordEncoder passwordEncoder;
    private final CardRepository cardRepository;

    OperationsController(PasswordEncoder passwordEncoder, CardRepository cardRepository) {
        this.passwordEncoder = passwordEncoder;
        this.cardRepository = cardRepository;
    }

    @GetMapping("/gethash")
    public String getHash(@RequestParam("password") String password) {
        return passwordEncoder.encode(password);
    }

    @GetMapping("/verifyhash")
    public boolean verifyHash(@RequestParam("password") String password, @RequestParam("hash") String encoded ) {
        return passwordEncoder.matches(password, encoded);
    }

    @GetMapping("/")
    public List<Card> getCardsRepository() { return cardRepository.getCards();
    }
}
