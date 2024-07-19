# Implementing Repository Interface with JPA Query Method Feature

## 목표
- [JPA Query Method](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html) 기능을 직접 구현해보기
- Reflection과 Proxy를 적용해보는 연습하기
- 이전에 배웠던 TDD를 개발 과정에 적용하며 진행하기

## Feature
- MyJpaRepository를 상속한 Repository Interface의 Method에 해당하는 Query 생성.
- Repository method 호출 시 Proxy 객체가 가로채서 동작.

### Principle
1. MyJpaRepository를 상속한 Repository의 Method를 파싱하여 PrepareStatement Query 생성한다. 
2. Query는 인자 부분을 남겨두고 캐싱해둔다. 
3. ProxyFactoryBean을 사용하여 MyJpaRepository을 상속한 Repository Interface의 Prxoy Bean을 생성한다. 
4. Repository의 Method를 호출하면 Proxy 객체에서 Query에 동적인 값 인자를 결합하여 Query문을 완성한다.

### Structure

![Diagram](https://github.com/user-attachments/assets/b898f187-1910-446c-b8ec-4249c14eba8f)

## 사용한 것
### Java feature
- Reflection
- JDK Dynamic Proxy
- ProxyFactoryBean
- Custom Annotation

### Behavior
- TDD 연습
- 일급 컬렉션 적용


## 회고
### 어려웠던 점
- 처음에 JDK Dynamic Proxy를 적용하려고 InvocationHandler를 상속해서 구현하고, Test에서 Proxy 객체 생성하는건 성공했는데, 결국 Spring에서 사용하려면 Bean으로 등록해야해서 이부분이 제일 고민이었다.
- 기존에 생각했던 것은 BeanPostProcessor로 객체를 바꿔치기 등록하는 것이였지만, Repository가 Interface라 구현체가 없어서 어떻게해야하는지 한참 고민함.
- ProxyFactoryBean known example들은 다 구체 클래스를 target으로 지정했으나, 문서에서 함수들을 찾다보니 target Interface를 지정하는  setInterface 함수가 있었다.

### 느낀점
- 실무에서는 왠만하면 `@Aspect`를 가져다 쓰기만 했기 때문에, 책이나 문서로만 읽고 넘어갔던 부분들을 직접 구현할 수 있어서 재미있었다.
- 파싱하는 부분은 Copilot이나 GPT한테 시키려고 했는데, 자꾸 제대로 못해서 그냥 내가 직접 구현했다.
- Parser를 직접 구현하면서 이전에 Next Step에서 배웠던 일급 컬렉션이나 객체 중심적으로 개발하는 과정을 다시 한번 적용해봤다.
- TDD 연습을 하면서, 평소에 API 하나 전체 구현 후 테스트 하던 방식에서 작은 클래스 하나 틀만 만들어서 테스트를 작성해 둔 다음, 구현하면서 틀리는 부분을 계속 수정했더니 확인 과정이 더 빨라졌다.
- 객체를 계속 나누면서 리팩토링을 하다보니 이전에 만들어둔 테스트를 빠르게 돌려보기만 하면되는 점이 좋았다.
- 좀 더 확장해본다면, 지금은 Query 만들어서 출력까지 했는데, 실제 조회하고 이를 기반으로 Entity를 만들거나 1차 캐시도 구현해보는 것도 좋을 것 같다.