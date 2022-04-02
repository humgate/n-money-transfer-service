package com.humga.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ResponseErrDTO {
    String message;
    int id;
}
