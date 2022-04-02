package com.humga.moneytransferservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConfirmOperationRequestDTO {
     String operationId;
     String code;
}
