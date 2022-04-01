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

    //дата действия - хэш
    private String validThrough;

    //Владелец - хэш
    @EqualsAndHashCode.Exclude
    private String owner;

    // CVV код - хэш
    private String cvv;

    //пустой конструктор для Jackon десериализации
    Card () {

    }
}
