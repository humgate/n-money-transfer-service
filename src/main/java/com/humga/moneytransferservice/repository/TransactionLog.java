package com.humga.moneytransferservice.repository;

import com.humga.moneytransferservice.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TransactionLog {
    private final Map<Long, Transaction> log = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong();

    public void put(Transaction transaction) {
        long currentId = id.incrementAndGet();
        transaction.setId(currentId);
        log.put(currentId,transaction);
    }
}
