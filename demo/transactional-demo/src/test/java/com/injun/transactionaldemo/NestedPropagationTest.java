package com.injun.transactionaldemo;

import com.injun.transactionaldemo.member.DemoMemberRepository;
import com.injun.transactionaldemo.service.NestedOuterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NestedPropagationTest {

    @Autowired
    NestedOuterService outerService;

    @Autowired
    DemoMemberRepository repository;

    @BeforeEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    void NESTED_지원여부_확인() {

        outerService.execute();

        System.out.println();
        System.out.println("===== NESTED 검증 결과 =====");
        System.out.println("최종 DB 데이터 개수 = " + repository.count());

        repository.findAll().forEach(member ->
                System.out.println("남은 데이터 = " + member.getName())
        );

        System.out.println("============================");
        System.out.println();
    }
}