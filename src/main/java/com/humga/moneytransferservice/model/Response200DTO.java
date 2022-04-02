package com.humga.moneytransferservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Response200DTO {
    String operationId;
    @JsonInclude(JsonInclude.Include.NON_NULL) //если поле null, то оно не будет сериализовыватcя в Json
    String code;
}
