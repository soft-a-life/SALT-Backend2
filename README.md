
# SALT / spring-back Proj.

이 프로젝트는 Spring을 기반으로 한 웹 애플리케이션입니다.

## 개요

이 프로젝트는 사용자 등록, 로그인, 게시물 작성 등의 기능을 제공하는 백엔드 서버입니다. Spring Boot를 사용하여 개발되었습니다.

- `src/main/java`: Java 소스 코드를 포함하는 디렉토리입니다. 컨트롤러, 모델, 서비스 등의 클래스가 여기에 위치합니다.
- `src/main/resources`: 프로퍼티 파일, 정적 리소스, 템플릿 파일 등을 포함하는 디렉토리입니다.
- `build.gradle`: Gradle 프로젝트 설정 파일입니다.

## 의존성

이 프로젝트는 다음과 같은 주요 의존성을 사용합니다:

- Spring Boot Starter: Spring Boot의 핵심 의존성으로, Spring Boot 애플리케이션을 구성하는 데 사용됩니다.
- Spring Web: Spring MVC를 사용하여 웹 애플리케이션을 개발하는 데 필요한 의존성입니다.
- Spring Data JPA: 데이터베이스와의 상호작용을 위한 JPA 구현체인 Hibernate를 사용하기 위한 의존성입니다.
- ...

## 설치 및 실행

다음은 프로젝트를 설치하고 실행하는 단계입니다:

1. Git 저장소에서 프로젝트를 클론합니다: `git clone https://github.com/yourusername/my-spring-project.git`
2. 프로젝트 디렉토리로 이동합니다: `cd my-spring-project`
3. Gradle을 사용하여 프로젝트를 빌드합니다: `gradle build`
4. 애플리케이션을 실행합니다: `gradle bootRun`
5. 웹 브라우저에서 `http://localhost:8080`으로 접속하여 애플리케이션을 확인합니다.

## 기여

이 프로젝트에 기여하려면 다음 단계를 따르세요:

1. 이 저장소를 포크합니다.
2. 새 브랜치를 생성합니다: `git checkout -b feature/my-new-feature`
3. 변경 사항을 커밋합니다: `git commit -am 'Add some feature'`
4. 포크한 저장소로 푸시합니다: `git push origin feature/my-new-feature`
5. Pull Request를 생성합니다.

## CommitMesseageRoule
``` form
<type>(<scope>) : <subject>          -- 헤더
<BLANK LINE>
<body>                              -- 본문
<BLANK LINE>
<footer>                            -- 바닥글
```
## type

```bash
feat : 새로운 기능에 대한 커밋
fix : 버그 수정에 대한 커밋
build : 빌드 관련 파일 수정에 대한 커밋
chore : 그 외 자잘한 수정에 대한 커밋
ci : CI관련 설정 수정에 대한 커밋
docs : 문서 수정에 대한 커밋
style : 코드 스타일 혹은 포맷 등에 관한 커밋
refactor :  코드 리팩토링에 대한 커밋
test : 테스트 코드 수정에 대한 커밋
```

→ type은 해당 커밋의 성격을 나타내어야 하고 위 중 하나여야 한다.

## **<body>**

→ 본문으로 헤더로 표현할 수 없는 상세한 내용을 적는다.
헤더로 표현이 가능하다면 생략 가능하다.

## **<footer>**

→ 바닥글로 어떤 이슈에서 왔는지 같은 참조 정보들을 추가하는 용도로 사용한다

예를 들어 특정 이슈를 참조하려면

```
close #1233
```

과 같이 추가하면 된다. close는 이슈를 참조하면서 main브랜치로 푸시될 때 이슈를 닫게 된다.
