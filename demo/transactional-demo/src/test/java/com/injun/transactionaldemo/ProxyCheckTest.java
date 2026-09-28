package com.injun.transactionaldemo;

import com.injun.transactionaldemo.service.ProxyCheckService;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProxyCheckTest {

    @Autowired
    ProxyCheckService service;

    @Test
    void Proxy_private_final_검증() {

        boolean normalResult = service.normalMethod();
        boolean finalResult = service.finalMethod();
        boolean privateResult = service.callPrivateMethod();

        System.out.println();
        System.out.println("===== Proxy 검증 결과 =====");

        System.out.println("실제 Bean 클래스 = "
                + service.getClass().getName());

        System.out.println("AOP Proxy = "
                + AopUtils.isAopProxy(service));

        System.out.println("CGLIB Proxy = "
                + AopUtils.isCglibProxy(service));

        System.out.println();

        System.out.println("일반 public 메서드 = " + normalResult);
        System.out.println("final 메서드       = " + finalResult);
        System.out.println("private 메서드     = " + privateResult);

        System.out.println("===========================");
        System.out.println();

        assertTrue(normalResult);
        assertFalse(finalResult);
        assertFalse(privateResult);
    }
}