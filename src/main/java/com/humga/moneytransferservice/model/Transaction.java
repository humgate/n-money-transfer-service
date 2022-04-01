package com.humga.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NonNull
@AllArgsConstructor
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
}
