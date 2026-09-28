package com.injun.transactionaldemo;

import com.injun.transactionaldemo.service.SelfInvocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SelfInvocationTest {

    @Autowired
    SelfInvocationService service;

    @Test
    void selfInvocation_동작_비교() {

        boolean directCall = service.inner();

        boolean selfInvocation = service.outer();

        System.out.println();
        System.out.println("===== Self Invocation 검증 결과 =====");
        System.out.println("외부에서 inner() 직접 호출 = " + directCall);
        System.out.println("outer() -> inner() 내부 호출 = " + selfInvocation);
        System.out.println("====================================");
        System.out.println();

        assertTrue(directCall);
        assertFalse(selfInvocation);
    }
}