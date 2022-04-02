package com.humga.moneytransferservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransferRequestDTO {
     @Getter
     @Setter
     @ToString
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
