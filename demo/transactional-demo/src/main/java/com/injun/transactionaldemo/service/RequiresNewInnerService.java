package com.injun.transactionaldemo.service;

import com.injun.transactionaldemo.member.DemoMember;
import com.injun.transactionaldemo.member.DemoMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequiresNewInnerService {

    private final DemoMemberRepository repository;

    public RequiresNewInnerService(DemoMemberRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveInner() {
        repository.save(new DemoMember("INNER"));
    }
}