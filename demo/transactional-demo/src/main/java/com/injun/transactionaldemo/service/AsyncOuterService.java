package com.injun.transactionaldemo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncOuterService {

    private final AsyncCheckService asyncCheckService;

    public AsyncOuterService(AsyncCheckService asyncCheckService) {
        this.asyncCheckService = asyncCheckService;
    }

    @Transactional
    public CompletableFuture<Boolean> execute() {

        boolean active =
                TransactionSynchronizationManager.isActualTransactionActive();

        System.out.println();
        System.out.println("Outer Thread = "
                + Thread.currentThread().getName());

        System.out.println(
                "Outer Transaction Active = " + active
        );

        return asyncCheckService.checkTransaction();
    }
}