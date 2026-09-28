package com.injun.transactionaldemo;

import com.injun.transactionaldemo.member.DemoMemberRepository;
import com.injun.transactionaldemo.service.RequiredOuterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RequiredPropagationTest {

    @Autowired
    RequiredOuterService outerService;

    @Autowired
    DemoMemberRepository repository;

    @BeforeEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    void REQUIRED_같이_Rollback() {

        try {
            outerService.execute();
        } catch (RuntimeException e) {
            // 결과 확인을 위해 예외 무시
        }

        long count = repository.count();

        System.out.println();
        System.out.println("===== REQUIRED 검증 결과 =====");
        System.out.println("최종 DB 데이터 개수 = " + count);
        System.out.println("=============================");
        System.out.println();

        assertEquals(0, count);
    }
}