package com.injun.transactionaldemo.service;

import com.injun.transactionaldemo.member.DemoMember;
import com.injun.transactionaldemo.member.DemoMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NestedInnerService {

    private final DemoMemberRepository repository;

    public NestedInnerService(DemoMemberRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.NESTED)
    public void saveInner() {
        repository.save(new DemoMember("NESTED_INNER"));

        throw new RuntimeException("Nested 내부 실패");
    }
}