package com.humga.moneytransferservice.model;

import lombok.Data;

@Data
public class TransferRequestDTO {

     @Data
     public class AmountDTO {
          String currency;
          int value;
     }

     long cardFromNumber;
     long cardToNumber;
     short cardFromCVV;
     String cardFromValidTill;
     AmountDTO amount;
}
