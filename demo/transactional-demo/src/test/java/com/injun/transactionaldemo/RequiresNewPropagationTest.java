package com.injun.transactionaldemo;

import com.injun.transactionaldemo.member.DemoMemberRepository;
import com.injun.transactionaldemo.service.RequiresNewOuterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RequiresNewPropagationTest {

    @Autowired
    RequiresNewOuterService outerService;

    @Autowired
    DemoMemberRepository repository;

    @BeforeEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    void REQUIRES_NEW_별도_트랜잭션() {

        try {
            outerService.execute();
        } catch (RuntimeException e) {
            // 결과 확인을 위해 예외 무시
        }

        long outerCount = repository.findAll().stream()
                .filter(member -> member.getName().equals("OUTER"))
                .count();

        long innerCount = repository.findAll().stream()
                .filter(member -> member.getName().equals("INNER"))
                .count();

        System.out.println();
        System.out.println("===== REQUIRES_NEW 검증 결과 =====");
        System.out.println("OUTER 데이터 개수 = " + outerCount);
        System.out.println("INNER 데이터 개수 = " + innerCount);
        System.out.println("==================================");
        System.out.println();

        assertEquals(0, outerCount);
        assertEquals(1, innerCount);
    }
}