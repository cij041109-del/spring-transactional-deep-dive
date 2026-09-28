package com.injun.transactionaldemo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ProxyCheckService {

    @Transactional
    public boolean normalMethod() {
        return TransactionSynchronizationManager
                .isActualTransactionActive();
    }

    @Transactional
    public final boolean finalMethod() {
        return TransactionSynchronizationManager
                .isActualTransactionActive();
    }

    public boolean callPrivateMethod() {
        return privateMethod();
    }

    @Transactional
    private boolean privateMethod() {
        return TransactionSynchronizationManager
                .isActualTransactionActive();
    }
}