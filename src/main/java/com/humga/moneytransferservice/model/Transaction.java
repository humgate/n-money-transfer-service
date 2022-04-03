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
    private long from;

    //карта - получатель
    private long to;

    //сумма перевода
    private int value;

    //Валюта перевода
    private String currency;

    //Статус
    boolean success;

    public Transaction(long from, long to, int value, String currency) {
        this.from = from;
        this.to = to;
        this.value = value;
        this.currency = currency;
    }

    public void markDone() {
        timeStamp = LocalDateTime.now();
        setSuccess(true);
    }
}
