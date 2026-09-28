package com.injun.transactionaldemo.service;

import com.injun.transactionaldemo.member.DemoMember;
import com.injun.transactionaldemo.member.DemoMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NestedOuterService {

    private final DemoMemberRepository repository;
    private final NestedInnerService innerService;

    public NestedOuterService(
            DemoMemberRepository repository,
            NestedInnerService innerService
    ) {
        this.repository = repository;
        this.innerService = innerService;
    }

    @Transactional
    public void execute() {

        repository.save(new DemoMember("NESTED_OUTER"));

        try {
            innerService.saveInner();
        } catch (Exception e) {
            System.out.println("Inner 예외 = " + e.getClass().getSimpleName());
        }
    }
}