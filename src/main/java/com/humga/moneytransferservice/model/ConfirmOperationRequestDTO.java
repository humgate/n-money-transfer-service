package com.humga.moneytransferservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmOperationRequestDTO {
     String operationId;
     String code;
}
