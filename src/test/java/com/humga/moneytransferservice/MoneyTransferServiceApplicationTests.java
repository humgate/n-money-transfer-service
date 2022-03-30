package com.humga.moneytransferservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humga.moneytransferservice.model.Card;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MoneyTransferServiceApplicationTests {
    static final String CARDS_REPOSITORY_FILE_NAME = "cards.json";
    final PasswordEncoder passwordEncoder;

    @Autowired
    MoneyTransferServiceApplicationTests(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    void contextLoads() {
        //init(); раскомментировать для того чтобы при запуске теста сгенерировать cards.json
    }

    //метод для наполнения тестовыми данными репозитория карт
    public void init (){
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(1111000011110000L,
                passwordEncoder.encode("01/01/2022"),
                passwordEncoder.encode("IVAN IVANOV"),
                passwordEncoder.encode("111")));

        cards.add(new Card(1111222211112222L,
                passwordEncoder.encode("01/01/2022"),
                passwordEncoder.encode("PETER PETROV"),
                passwordEncoder.encode("222")));

        cards.add(new Card(1111222211112222L,
                passwordEncoder.encode("03/03/2022"),
                passwordEncoder.encode("JOHN DOE"),
                passwordEncoder.encode("333")));

        cards.add(new Card(1111222211113333L,
                passwordEncoder.encode("03/03/2022"),
                passwordEncoder.encode("JOHN DOE"),
                passwordEncoder.encode("444")));

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get(CARDS_REPOSITORY_FILE_NAME).toFile(), cards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
