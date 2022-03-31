package com.humga.moneytransferservice.controller;

import com.humga.moneytransferservice.model.Card;
import com.humga.moneytransferservice.model.ConfirmOperationDTO;
import com.humga.moneytransferservice.model.TransferRequestDTO;
import com.humga.moneytransferservice.repository.CardRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin //позволит фронту, загруженному из одного источника обращаться к приложению запущенному на другом
public class OperationsController {
    private final PasswordEncoder passwordEncoder;
    private final CardRepository cardRepository;

    OperationsController(PasswordEncoder passwordEncoder, CardRepository cardRepository) {
        this.passwordEncoder = passwordEncoder;
        this.cardRepository = cardRepository;
    }

    @PostMapping(value = "/transfer", produces = "application/json")
        public ResponseEntity<?> transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
        return new ResponseEntity<>("{\"operationId\": \"12345\"}", HttpStatus.OK);
    }

    @PostMapping(value = "/confirmOperation", produces = "application/json")
    public ResponseEntity<?> confirmOperation(@RequestBody ConfirmOperationDTO confirmOperationDTO) {
        return new ResponseEntity<>("{\"operationId\": \"12345\",\"code\": \"12345code\"}", HttpStatus.OK);
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
