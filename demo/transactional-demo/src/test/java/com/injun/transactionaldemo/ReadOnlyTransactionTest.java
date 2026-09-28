package com.injun.transactionaldemo;

import com.injun.transactionaldemo.member.DemoMember;
import com.injun.transactionaldemo.member.DemoMemberRepository;
import com.injun.transactionaldemo.service.ReadOnlyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReadOnlyTransactionTest {

    @Autowired
    ReadOnlyService readOnlyService;

    @Autowired
    DemoMemberRepository repository;

    @BeforeEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    void readOnly_동작_비교() {

        // 1. 일반 @Transactional
        Long normalId = readOnlyService.createMember("인준");

        readOnlyService.changeNormally(normalId);

        DemoMember normalMember =
                repository.findById(normalId).orElseThrow();

        String normalResult = normalMember.getName();


        // 2. @Transactional(readOnly = true)
        Long readOnlyId = readOnlyService.createMember("인준");

        readOnlyService.changeWithReadOnly(readOnlyId);

        DemoMember readOnlyMember =
                repository.findById(readOnlyId).orElseThrow();

        String readOnlyResult = readOnlyMember.getName();


        System.out.println();
        System.out.println("===== readOnly=true 검증 결과 =====");
        System.out.println("일반 @Transactional DB 결과 = " + normalResult);
        System.out.println("readOnly=true DB 결과       = " + readOnlyResult);
        System.out.println("==================================");
        System.out.println();


        assertEquals("일반변경", normalResult);
        assertEquals("인준", readOnlyResult);
    }
}