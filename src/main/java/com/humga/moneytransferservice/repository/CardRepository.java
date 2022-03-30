package com.humga.moneytransferservice.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humga.moneytransferservice.model.Card;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Repository
public class CardRepository {
    static final String CARDS_REPOSITORY_FILE_NAME = "cards.json";
    //энкодер для операций шифрования данных
    private final PasswordEncoder passwordEncoder;
    //"базу" карт зачитываем при инициализации из файла
    private final List<Card> cards = readCardRepository(CARDS_REPOSITORY_FILE_NAME);

    public CardRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Записывает список карт в json файл
     *
     * @param filename - имя json файла для записи
     */
    public void saveCardRepository(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get(filename).toFile(), cards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Зачитывает список карт из json файла
     *
     * @param filename - имя json c данными карт
     */
    public List<Card> readCardRepository(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(Paths.get(filename).toFile(), Card[].class));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось прочитать репозиторий карт. " + e.getMessage());
        }
    }
}


