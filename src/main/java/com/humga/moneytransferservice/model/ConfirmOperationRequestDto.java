package com.humga.moneytransferservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmOperationRequestDto {
     String operationId;
     String code;
}
