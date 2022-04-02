package com.humga.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseErrDTO {
    String message;
    int id;
}
