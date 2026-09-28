package com.injun.transactionaldemo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class SelfInvocationService {

    public boolean outer() {
        return inner();
    }

    @Transactional
    public boolean inner() {
        return TransactionSynchronizationManager
                .isActualTransactionActive();
    }
}