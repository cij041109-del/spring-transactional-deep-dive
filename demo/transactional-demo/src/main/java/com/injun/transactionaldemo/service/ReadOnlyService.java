package com.injun.transactionaldemo.service;

import com.injun.transactionaldemo.member.DemoMember;
import com.injun.transactionaldemo.member.DemoMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReadOnlyService {

    private final DemoMemberRepository repository;

    public ReadOnlyService(DemoMemberRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Long createMember(String name) {
        DemoMember member = new DemoMember(name);
        repository.save(member);

        return member.getId();
    }

    @Transactional
    public void changeNormally(Long id) {
        DemoMember member = repository.findById(id)
                .orElseThrow();

        member.changeName("일반변경");
    }

    @Transactional(readOnly = true)
    public void changeWithReadOnly(Long id) {
        DemoMember member = repository.findById(id)
                .orElseThrow();

        member.changeName("READ_ONLY_변경");
    }
}