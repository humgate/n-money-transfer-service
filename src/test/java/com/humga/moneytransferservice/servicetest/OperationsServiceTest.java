package com.humga.moneytransferservice.servicetest;

import com.humga.moneytransferservice.model.*;
import com.humga.moneytransferservice.repository.TransactionLog;
import com.humga.moneytransferservice.service.AcquiringService;
import com.humga.moneytransferservice.service.OperationsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class OperationsServiceTest {
    /**
     * Тест метода transfer при успешном выполнении перевода
     *
     */
    @Test
    void transferTest() {
        //given:
        //mock для ipsp
        AcquiringService ipsp = mock(AcquiringService.class);

        when(ipsp.authorizeTransaction(isA(TransactionAuthorizationRequest.class))).
        thenReturn(AuthorizationStatus.AUTHORIZED);

        when(ipsp.verifyConfirmationCode(isA(String.class),isA(String.class))).thenReturn(true);

        //mock для transaction
        Transaction transaction = mock(Transaction.class);
        when(transaction.getOperationId()).thenReturn(1L);

        //класс простейший, поэтому в тесте будет работать его реальный экземпляр, не мок
        TransactionLog transactionLog = new TransactionLog();

        //входной параметр для тестируемого метода
        TransferRequestDTO reqDTO = new TransferRequestDTO();
        reqDTO.setCardFromNumber(1111_1111_1111_1111L);
        reqDTO.setCardToNumber(1111_1111_1111_1111L);
        reqDTO.setCardFromCVV("111");
        TransferRequestDTO.AmountDto amountDto = new TransferRequestDTO.AmountDto();
        amountDto.setValue(1000);
        amountDto.setCurrency("RUR");
        reqDTO.setAmount(amountDto);
        reqDTO.getAmount().setCurrency("RUR");

        //экземпляр сервиса
        OperationsService service = new OperationsService(transactionLog, ipsp);

        //when:
        Response200DTO respDTO = service.transfer(reqDTO);

        //then:
        Assertions.assertEquals(respDTO.getOperationId(),"1");
    }
}
