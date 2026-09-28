package com.injun.transactionaldemo;

import com.injun.transactionaldemo.service.AsyncOuterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class AsyncTransactionTest {

    @Autowired
    AsyncOuterService outerService;

    @Test
    void Async에서는_기존_트랜잭션이_이어지지_않는다() {

        boolean asyncResult =
                outerService.execute().join();

        System.out.println();
        System.out.println("===== @Async 검증 결과 =====");
        System.out.println(
                "Async Transaction Active = " + asyncResult
        );
        System.out.println("===========================");
        System.out.println();

        assertFalse(asyncResult);
    }
}