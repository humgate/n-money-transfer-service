package com.humga.moneytransferservice.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {
    //таймстэмп операции
    private LocalDateTime timeStamp;

    //карта - источник
    private Card source;

    //карта - получатель
    private Card target;

    //результат
    private boolean successful;
}
