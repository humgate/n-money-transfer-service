package com.humga.moneytransferservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDto {
     @Getter
     @Setter
     public class AmountDto {
          String currency;
          int value;
     }

     long cardFromNumber;
     long cardToNumber;
     String cardFromCVV;
     String cardFromValidTill;
     AmountDto amount;
}
