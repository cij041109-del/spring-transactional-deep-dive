package com.injun.transactionaldemo.service;

import com.injun.transactionaldemo.member.DemoMember;
import com.injun.transactionaldemo.member.DemoMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequiresNewOuterService {

    private final DemoMemberRepository repository;
    private final RequiresNewInnerService innerService;

    public RequiresNewOuterService(
            DemoMemberRepository repository,
            RequiresNewInnerService innerService
    ) {
        this.repository = repository;
        this.innerService = innerService;
    }

    @Transactional
    public void execute() {

        repository.save(new DemoMember("OUTER"));

        innerService.saveInner();

        throw new RuntimeException("Outer 실패");
    }
}