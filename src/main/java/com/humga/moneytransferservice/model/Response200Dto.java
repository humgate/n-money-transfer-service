package com.humga.moneytransferservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class Response200Dto {
    String operationId;
    @JsonInclude(JsonInclude.Include.NON_NULL) //если поле null, то оно не будет сериализовыватcя в Json
    String code;
}
