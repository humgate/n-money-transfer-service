package com.humga.moneytransferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseErrDTO {
    String message;
    int id;
}
