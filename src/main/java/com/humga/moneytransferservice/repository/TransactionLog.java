package com.humga.moneytransferservice.repository;

import com.humga.moneytransferservice.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionLog {
    private final List<Transaction> log = new ArrayList<>();
}
