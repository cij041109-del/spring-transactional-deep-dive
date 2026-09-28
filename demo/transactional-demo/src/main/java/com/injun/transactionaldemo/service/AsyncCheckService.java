package com.injun.transactionaldemo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncCheckService {

    @Async
    public CompletableFuture<Boolean> checkTransaction() {

        boolean active =
                TransactionSynchronizationManager.isActualTransactionActive();

        System.out.println(
                "Async Thread = " + Thread.currentThread().getName()
        );

        System.out.println(
                "Async Transaction Active = " + active
        );

        return CompletableFuture.completedFuture(active);
    }
}