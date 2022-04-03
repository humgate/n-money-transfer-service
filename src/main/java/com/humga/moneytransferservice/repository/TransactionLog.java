package com.humga.moneytransferservice.repository;

import com.humga.moneytransferservice.model.Transaction;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.nio.file.StandardOpenOption.*;

/**
 * Хранилище лога транзакций
 *
 */
@Repository
public class TransactionLog {
    private final Map<Long, Transaction> log = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong();
    private final Path logFilePath = Paths.get("transactionlog.log");


    public void writeToLogFile(Transaction transaction) {
        try {
            Files.write(logFilePath, transaction.toString().getBytes(), WRITE, CREATE, APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /**
     * Записывает транзакцию в лог транзакций
     *
     * @param transaction - транзакция
     */
    public void put(Transaction transaction) {
        long currentId = id.incrementAndGet();
        transaction.setOperationId(currentId);
        log.put(currentId,transaction);
    }

    public Transaction get(long operationId) {
        return log.get(operationId);
    }

    public void update(long operationId, Transaction transaction) {
        log.put(operationId, transaction);
    }
}
