package com.injun.transactionaldemo.service;

import com.injun.transactionaldemo.member.DemoMember;
import com.injun.transactionaldemo.member.DemoMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequiredInnerService {

    private final DemoMemberRepository repository;

    public RequiredInnerService(DemoMemberRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveInner() {
        repository.save(new DemoMember("INNER"));
    }
}