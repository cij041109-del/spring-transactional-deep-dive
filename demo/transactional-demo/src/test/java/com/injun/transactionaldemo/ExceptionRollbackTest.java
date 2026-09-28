package com.injun.transactionaldemo;

import com.injun.transactionaldemo.member.DemoMemberRepository;
import com.injun.transactionaldemo.service.ExceptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ExceptionRollbackTest {

    @Autowired
    ExceptionService exceptionService;

    @Autowired
    DemoMemberRepository repository;

    @BeforeEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    void 예외에_따른_Rollback_비교() {

        // 1. RuntimeException
        try {
            exceptionService.runtimeException();
        } catch (RuntimeException e) {
            // 테스트 계속 진행
        }

        long runtimeCount = repository.count();

        // 다음 실험을 위해 DB 초기화
        repository.deleteAll();


        // 2. Checked Exception
        try {
            exceptionService.checkedException();
        } catch (Exception e) {
            // 테스트 계속 진행
        }

        long checkedCount = repository.count();


        System.out.println();
        System.out.println("===== 예외 Rollback 검증 결과 =====");
        System.out.println("RuntimeException 발생 후 DB 개수 = " + runtimeCount);
        System.out.println("Checked Exception 발생 후 DB 개수 = " + checkedCount);
        System.out.println("==================================");
        System.out.println();


        assertEquals(0, runtimeCount);
        assertEquals(1, checkedCount);
    }
}