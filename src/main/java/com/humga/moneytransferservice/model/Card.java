package com.humga.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Card {
    //номер карты в открытом виде
    private long number;

    //дата действия - хэш
    private String validThrough;

    //Владелец - хэш
    private String owner;

    // CVV код - хэш
    private String cvv;

    //пустой конструктор для Jackon десериализации
    Card () {

    }
}
