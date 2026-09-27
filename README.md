# Spring Transactional Deep Dive

Spring의 `@Transactional`이 예상과 다르게 동작하는 상황을 직접 재현하고,
AOP 프록시와 트랜잭션 경계를 중심으로 내부 동작을 정리하는 리서치입니다.

## Research Topic

A1. `@Transactional`이 안 먹는 순간들

## Main Topics

- Spring AOP Proxy
- JDK Dynamic Proxy vs CGLIB
- Self Invocation
- private / final 메서드
- Checked Exception Rollback
- `REQUIRES_NEW`
- `NESTED`
- `readOnly = true`
- `@Async`와 트랜잭션 경계

## Structure

- `docs/`: 리서치 본문 및 공식 문서 정리
- `demo/`: 실패 케이스 재현용 Spring Boot 프로젝트
