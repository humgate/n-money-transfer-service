package com.humga.moneytransferservice.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NonNull
public class Transaction {
    //id операции
    private long id;

    //таймстэмп операции
    private LocalDateTime timeStamp;

    //карта - источник
    private Card from;

    //карта - получатель
    private Card to;

    //сумма перевода
    private int value;

    //Валюта перевода
    private String currency;

    public Transaction(long id, LocalDateTime timeStamp, Card from, Card to, int value, String currency) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.from = from;
        this.to = to;
        this.value = value;
        this.currency = currency;
    }
}
