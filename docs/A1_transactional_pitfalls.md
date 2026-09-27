# A1. @Transactional이 안 먹는 순간들

## 1. 주제문
@Transactional은 단순히 메서드에 붙인다고 항상 동작하는 것이 아니다.
Spring의 트랜잭션은 AOP 프록시를 기반으로 동작하기 때문에,
호출 방식과 메서드 구조, 예외 종류, 스레드 경계에 따라
개발자의 예상과 다른 결과가 발생할 수 있다.

---

## 2. 왜 이 주제를 다루는가

Spring 백엔드에서 @Transactional은 매우 자주 사용된다.

보통 다음 정도로 이해하고 사용하기 쉽다.

- 붙이면 트랜잭션이 시작된다.
- 예외가 발생하면 롤백된다.
- readOnly=true면 읽기 전용이다.
- 다른 @Transactional 메서드를 호출하면 그 설정도 적용된다.

하지만 실제 동작은 이보다 복잡하다.

이번 발표에서는
"@Transactional이 왜 예상대로 동작하지 않는가?"를
Spring AOP 프록시 구조를 중심으로 살펴본다.

---

## 3. 먼저 알아야 할 핵심: @Transactional은 어떻게 동작하는가

### 3.1 @Transactional 자체가 트랜잭션을 여는 것이 아니다

### 3.2 Spring AOP Proxy

호출 흐름

Client
↓
Proxy
↓
TransactionInterceptor
↓
실제 Service 메서드

### 3.3 JDK Dynamic Proxy vs CGLIB

- JDK Dynamic Proxy
- CGLIB Proxy
- 차이점
- Spring에서는 언제 어떤 방식이 사용되는가

---

## 4. 흔한 오해와 실패 케이스

### 4.1 Self Invocation

오해:
같은 클래스 안에서 @Transactional 메서드를 호출해도 적용될 것이다.

실제:
프록시를 거치지 않고 this.method()가 호출되기 때문에
트랜잭션 부가기능이 적용되지 않을 수 있다.

### 4.2 private / final 메서드

오해:
메서드에 @Transactional만 붙이면 된다.

실제:
프록시 기반 AOP 특성상 메서드 가시성과 오버라이딩 가능 여부가 영향을 준다.

### 4.3 Checked Exception

오해:
예외가 발생하면 무조건 rollback 된다.

실제:
기본적으로 RuntimeException / Error가 rollback 대상이다.
Checked Exception은 기본 rollback 대상이 아니다.

### 4.4 REQUIRES_NEW

외부 트랜잭션과 내부 트랜잭션을 분리하면
어떤 결과가 발생하는가?

### 4.5 NESTED

REQUIRES_NEW와 NESTED는 무엇이 다른가?

### 4.6 @Async + @Transactional

트랜잭션은 현재 스레드와 연결된다.

@Async로 다른 스레드에서 실행되면
기존 트랜잭션 컨텍스트가 그대로 전달되지 않는다.

---

## 5. readOnly = true의 진짜 의미

오해:
readOnly=true면 DB UPDATE가 절대 불가능하다.

확인할 것:

- Hibernate FlushMode
- Dirty Checking
- JDBC readOnly hint
- DB가 실제 쓰기를 막는지

---

## 6. 실패 케이스 직접 재현

### Demo 1. Self Invocation
### Demo 2. private / final
### Demo 3. Checked Exception rollback
### Demo 4. REQUIRES_NEW
### Demo 5. NESTED
### Demo 6. @Async + Transaction

각 데모는

1. 개발자가 예상한 결과
2. 실제 코드
3. 실제 실행 결과
4. 왜 이런 결과가 나왔는가

순서로 설명한다.

---

## 7. 그래서 어떻게 설계해야 하는가

- 트랜잭션 경계를 명확하게 잡기
- Service 메서드 책임 분리
- self-invocation 피하기
- rollbackFor 남용하지 않기
- propagation을 목적 없이 사용하지 않기
- 비동기 작업과 트랜잭션 경계를 분리해서 생각하기

---

## 8. 트레이드오프 / 반론

- 모든 Service 메서드에 @Transactional을 붙이는 것이 좋은가?
- readOnly=true는 꼭 붙여야 하는가?
- REQUIRES_NEW를 많이 사용하면 어떤 문제가 생기는가?
- 프록시 기반 트랜잭션의 한계는 무엇인가?

---

## 9. 우리 프로젝트에 적용한다면

실제 멋사 프로젝트의 Service 계층을 기준으로

- 회원 생성
- 과제 제출
- 알림 전송
- 데이터 수정

등의 트랜잭션 경계를 점검한다.

---

## 10. 예상 질문 5개

### Q1.
왜 같은 클래스 안에서 호출하면 @Transactional이 안 먹나요?

### Q2.
Checked Exception도 rollback하고 싶으면 어떻게 하나요?

### Q3.
REQUIRES_NEW와 NESTED의 가장 큰 차이는 무엇인가요?

### Q4.
readOnly=true인데 UPDATE SQL을 실행하면 어떻게 되나요?

### Q5.
@Transactional과 @Async를 같이 쓰면 왜 문제가 생기나요?

---

## 11. 참고한 1차 자료

- Spring Framework Reference Documentation
- Spring Framework Transaction 관련 공식 문서
- Spring AOP 공식 문서
- Hibernate ORM 공식 문서
