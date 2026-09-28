package com.injun.transactionaldemo.service;

import com.injun.transactionaldemo.member.DemoMember;
import com.injun.transactionaldemo.member.DemoMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExceptionService {

    private final DemoMemberRepository repository;

    public ExceptionService(DemoMemberRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void runtimeException() {
        repository.save(new DemoMember("RuntimeException"));

        throw new RuntimeException("RuntimeException 발생");
    }

    @Transactional
    public void checkedException() throws Exception {
        repository.save(new DemoMember("CheckedException"));

        throw new Exception("Checked Exception 발생");
    }
}