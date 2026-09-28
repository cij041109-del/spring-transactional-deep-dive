package com.injun.transactionaldemo.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoMemberRepository
        extends JpaRepository<DemoMember, Long> {
}