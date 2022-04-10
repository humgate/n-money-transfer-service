package com.humga.moneytransferservice.dto;

import lombok.Data;

@Data
public class TransferRequestDTO {
     @Data
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
