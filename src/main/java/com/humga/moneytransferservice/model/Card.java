package com.humga.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Card {
    //номер карты в открытом виде
    private long number;

    //дата действия
    private String validThrough;

    //Владелец
    private String owner;

    // CVV
    private String cvv;

    //пустой конструктор для Jackon десериализации
    Card () {

    }
}
