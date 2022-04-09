package com.humga.moneytransferservice.repositorytest;

import com.humga.moneytransferservice.model.Transaction;
import com.humga.moneytransferservice.repository.TransactionLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;


public class TransactionLogTest {
    //given
    static TransactionLog transactionLog = Mockito.spy(TransactionLog.class);

    @BeforeAll
    public static void initSuite() {
        ReflectionTestUtils.setField(transactionLog, "transactionLogFile", "transactionlogTest.log");
    }


    @Test
    void putAndGetTransactionLogTest() {
        //when
        transactionLog.put(new Transaction(
                1111_1111_1111_1111L,2222_2222_2222_2222L,1000, "RUR", false));
        Transaction tr = transactionLog.get(1L);
        //then
        Assertions.assertEquals(tr.getOperationId(),1L);
        Assertions.assertEquals(tr.getFrom(),1111_1111_1111_1111L);
        Assertions.assertEquals(tr.getTo(),2222_2222_2222_2222L);
        //время транзакции не ранее 2 секунд до момента проверки
        Assertions.assertTrue(tr.getTimeStamp().isAfter(LocalDateTime.now().minusSeconds(2)));
        Assertions.assertEquals(tr.getCurrency(), "RUR");
        Assertions.assertEquals(tr.getValue(), 1000);
        Assertions.assertFalse(tr.isSuccess());
    }




}
