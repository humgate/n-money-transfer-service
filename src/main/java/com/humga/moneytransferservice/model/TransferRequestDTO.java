package com.humga.moneytransferservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDTO {
     @Getter
     @Setter
     public static class AmountDto {
          String currency;
          int value;
     }

     long cardFromNumber;
     long cardToNumber;
     String cardFromCVV;
     String cardFromValidTill;
     AmountDto amount;
}
