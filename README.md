# Spring Transactional Deep Dive

`@Transactional`은 애노테이션을 붙이는 것만으로 항상 기대대로 동작하는 것이 아니며, Spring의 프록시 구조와 트랜잭션 경계에 따라 실제 동작이 달라질 수 있다.

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
