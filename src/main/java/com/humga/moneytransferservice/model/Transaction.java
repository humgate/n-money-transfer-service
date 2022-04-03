package com.humga.moneytransferservice.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NonNull
public class Transaction {
    //id операции
    @Setter
    private long id;

    //таймстэмп операции
    private LocalDateTime timeStamp;

    //карта - источник
    private final long from;

    //карта - получатель
    private final long to;

    //сумма перевода
    private final int value;

    //Валюта перевода
    private final String currency;

    //Статус
    @Setter
    boolean success;

    public Transaction(long from, long to, int value, String currency) {
        this.from = from;
        this.to = to;
        this.value = value;
        this.currency = currency;
    }

    public Transaction setSuccess() {
        timeStamp = LocalDateTime.now();
        setSuccess(true);
        return this;
    }

    public Transaction setFail() {
        timeStamp = LocalDateTime.now();
        setSuccess(false);
        return this;
    }
}
