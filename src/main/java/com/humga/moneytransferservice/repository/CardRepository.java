package com.humga.moneytransferservice.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humga.moneytransferservice.model.Card;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Repository
@Getter
public class CardRepository {
    //имя файла с "базой данных" карт зачитываем из application.properties
    @Value("${application.profile.cardsfile}")
    private String cardsfile;

    //энкодер для операций шифрования данных
    private PasswordEncoder passwordEncoder;

    //репозиторий карт
    private List<Card> cards;

    /* cardsfile зачитывается из application.properties после выполнения конструктора, поэтому
     * зачитывать данные из файла репозитория карт нужно сразу после конструктора. Используем @PostConstruct */
    @PostConstruct
    public void init() {
        cards = readCardRepository(cardsfile);
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


