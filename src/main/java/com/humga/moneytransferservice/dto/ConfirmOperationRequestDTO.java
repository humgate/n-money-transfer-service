package com.humga.moneytransferservice.dto;

import lombok.Data;

@Data
public class ConfirmOperationRequestDTO {
     String operationId;
     String code;
}
