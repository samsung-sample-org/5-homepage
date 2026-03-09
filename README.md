# 5. 홈페이지 시스템 - JDK 17 마이그레이션 라이브러리 검증

## 목차

1. [개요](#1-개요)
2. [기술 스택](#2-기술-스택)
3. [라이브러리 전환 현황 (ASIS → TOBE)](#3-라이브러리-전환-현황-asis--tobe)
   - [전환 원칙](#전환-원칙)
   - [전환 요약](#전환-요약)
   - [전체 라이브러리 매트릭스](#전체-라이브러리-매트릭스)
4. [솔루션/자체 라이브러리](#4-솔루션자체-라이브러리)
5. [주요 결정 사항과 근거](#5-주요-결정-사항과-근거)
6. [프로젝트 구조](#6-프로젝트-구조)
7. [실행 방법](#7-실행-방법)
8. [테스트 실행](#8-테스트-실행)
9. [알려진 제약사항](#9-알려진-제약사항)

---

## 1. 개요

기존 홈페이지 시스템(Spring Framework 3.x, JDK 8)을 JDK 17 + Spring Boot 3.5.11로 전환하기 위한 **라이브러리 호환성 검증 프로젝트**이다.

원본 시스템에서 사용 중인 오픈소스 라이브러리를 분석하여, Spring Boot 3 환경에서의 호환 여부를 Docker 기반으로 실증 검증한다. 각 라이브러리에 대해 단위 테스트를 작성하고, JDK 17 + Jakarta EE 10 환경에서 정상 동작함을 확인하는 것이 목적이다.

**6-company와의 차이점**: JDOM2, XMLBeans, Gson, commons-compress, commons-math3, Lucy XSS Servlet 등의 라이브러리가 추가 검증 대상이다. JSP는 6-company와 동일하게 유지한다.

---

## 2. 기술 스택

| 항목 | 선택 | 선택 이유 |
|------|------|-----------|
| JDK | 17 (Adoptium Temurin) | Spring Boot 3의 최소 요구사항. LTS 버전으로 장기 지원 보장 |
| Framework | Spring Boot 3.5.11 | 3.x 최신 안정 버전. Jakarta EE 10 기반, Spring Framework 6.1 내장 |
| 빌드 도구 | Maven | 기존 시스템과 동일한 빌드 도구를 유지하여 전환 비용 최소화 |
| View | **JSP** (기존 유지) | 기존 JSP 기반 화면 유지. tomcat-embed-jasper + JSTL 추가 |
| 패키징 | WAR | JSP 사용을 위해 WAR 패키징 필요 |
| 컨테이너 OS | Docker (CentOS 7) | JDK 6/7/8/17 모두 지원 가능. 레거시 환경 재현에 적합 |
| DB (로컬) | H2 인메모리 | Oracle 없이 로컬에서 빠른 테스트 가능 |

---

## 3. 라이브러리 전환 현황 (ASIS → TOBE)

### 전환 원칙

1. **기존 기술 최대 유지**: 있던 라이브러리를 그대로 가져간다. 마이그레이션 공수를 최소화한다.
2. **버전업 우선**: 동일 라이브러리의 최신 버전으로 업그레이드하는 것을 1순위로 한다.
3. **교체는 불가피한 경우만**: 버전업만으로 대응할 수 없을 때(프로젝트 폐기, Jakarta 미호환 등)에만 대체 라이브러리를 고려한다.
4. **JSP 유지**: 기존 JSP 기반 화면을 유지한다. tomcat-embed-jasper + JSTL로 Spring Boot 3에서 JSP를 지원한다.

### 전환 요약

| 전환 방식 | 건수 | 설명 |
|----------|------|------|
| Boot 내장 | 10건 | Spring Boot Starter/BOM에 포함. 별도 의존성 관리 불필요 |
| 버전업 | 20건 | 동일 계열 라이브러리 최신 버전으로 업그레이드 |
| 교체 | 3건 | 버전업 불가, 대체 라이브러리로 전환 (불가피) |
| 제거 | 8건 | 프로젝트 폐기/완전 EOL로 버전업 자체 불가, 또는 REST 전환으로 불필요 |
| **합계** | **41건** | 솔루션/자체 라이브러리 10건 별도 |

### 전체 라이브러리 매트릭스

> **범례** — Boot 내장: Spring Boot Starter가 관리 | 버전업: 동일 계열 최신 버전 | 교체: 대체 라이브러리 전환(불가피) | 제거: EOL/폐기/불필요

#### Framework / Core

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 1 | Spring 3.x (spring-webmvc, beans, context 등) | Spring 6.1+ | Boot 내장 | starter-web. javax→jakarta 전환 |
| 2 | Hibernate 3.1 | Hibernate 6.x | Boot 내장 | starter-data-jpa. javax.persistence→jakarta.persistence |
| 3 | Hibernate Validator 4.1 | Validator 8.x | Boot 내장 | starter-validation. javax.validation→jakarta.validation |
| 4 | aopalliance 1.0 + AspectJ 1.6.8 | AspectJ 1.9.21+ | Boot 내장 | starter-aop에 포함 |
| 5 | cglib 2.x | ByteBuddy | Boot 내장 | Spring 6이 프록시 생성기로 ByteBuddy 사용 |

#### JSON

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 6 | Jackson 1.x (org.codehaus.jackson) | Jackson 2.17+ | Boot 내장 | starter-web. org.codehaus→com.fasterxml 패키지 변경 |
| 7 | gson (구버전 또는 없음) | gson 2.10.1 | 버전업 | JSON 처리 유틸리티, 보안 취약점 패치 |

#### Logging

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 8 | Log4j 1.2.16 | **log4j-1.2-api** (Log4j2 브릿지) | 교체 | Log4j 1.x EOL(CVE-2019-17571). 1.x API 유지, Logback으로 라우팅 |
| 9 | SLF4J 1.6.4 | SLF4J 2.0+ | Boot 내장 | starter-logging 포함 |

#### Testing

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 10 | JUnit 4.9 | JUnit 5 (Jupiter) | Boot 내장 | starter-test. @RunWith→@ExtendWith 전환 |

#### Cache

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 11 | ehcache 1.2.3 / 2.5.0 | ehcache 3.10.8 | 버전업 | **classifier=jakarta 필수**. JCache(JSR-107) 표준 |

#### Scheduler

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 12 | quartz 1.6.1 | Quartz 2.3.2+ | 버전업 | starter-quartz. Boot 자동 구성으로 설정 간소화 |

#### HTTP

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 13 | commons-httpclient 3.x (EOL) | httpclient5 | 교체 | **commons-httpclient EOL**. groupId 완전 변경. org.apache.http→org.apache.hc |

#### Security

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 14 | antisamy 1.4.4 | antisamy 1.7.6 | 버전업 | XSS 필터 CVE 다수 패치 |
| 15 | lucy-xss (구버전) | lucy-xss-servlet 2.0.0 | 버전업 | Naver Lucy XSS 서블릿 필터 |

#### Office

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 16 | poi 3.x | poi 5.3.0 + poi-ooxml | 버전업 | .xlsx OOXML 지원 추가. XXE 취약점 패치 |
| 17 | jxl (Excel 라이브러리) | (제거) → POI 5.x | 제거 | jxl 프로젝트 폐기. POI로 완전 대체 |

#### XML

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 18 | dom4j 1.6.1 | dom4j 2.1.4 | 버전업 | groupId 변경(dom4j→org.dom4j). XXE 취약점 패치 |
| 19 | jdom 1.x | jdom2 2.0.6.1 | 버전업 | artifactId 변경(jdom→jdom2). JDK 17 호환 |
| 20 | xercesImpl 2.11.0 | xercesImpl 2.12.2 | 버전업 | XML 파싱 보안 패치 |
| 21 | xmlbeans (구버전/없음) | xmlbeans 5.2.0 | 버전업 | POI-OOXML XML 처리 의존성 |

#### SVG

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 22 | batik 1.7 (개별 batik-* 모듈) | batik-all 1.17 | 버전업 | 통합 모듈. SVG 렌더링 SSRF 방어 강화 |

#### Template

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 23 | velocity 1.7 | velocity-engine-core 2.4.1 | 버전업 | artifactId 변경. Boot 3에 auto-config 없음, 수동 Bean 필요 |

#### Apache Commons

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 24 | commons-beanutils 1.8.0 | commons-beanutils 1.9.4 | 버전업 | CVE-2014-0114 패치 |
| 25 | commons-codec 1.3 | commons-codec 1.16+ | Boot 내장 | Boot BOM 버전 자동 관리 |
| 26 | commons-collections 3.2 | commons-collections4 4.5.0 | 버전업 | **RCE 취약점(CVE-2015-6420) 패치 필수**. artifactId 변경 |
| 27 | commons-compress (없음/구버전) | commons-compress 1.26.1 | 버전업 | ZIP/TAR 보안 강화, 경로 순회 취약점 패치 |
| 28 | commons-io 1.4 | commons-io 2.16.1 | 버전업 | JDK 8+ NIO 통합 지원 |
| 29 | commons-lang 2.1 | commons-lang3 3.14+ | Boot 내장 | Boot BOM 관리. lang3 패키지로 import 변경 필요 |
| 30 | commons-math (없음/구버전) | commons-math3 3.6.1 | 버전업 | 수학/통계 연산 유틸리티 |
| 31 | commons-pool 1.5.3 | commons-pool2 | Boot 내장 | artifactId 변경. Boot BOM 관리 |
| 32 | commons-httpclient 3.x | (제거) | 제거 | EOL → httpclient5로 대체 |

#### Utility

| # | ASIS | TOBE | 방식 | 비고 |
|---|------|------|------|------|
| 33 | activation 1.1 (JAF) | jakarta.activation-api | 교체 | JDK 17에서 javax.activation 제거. jakarta 네임스페이스 전환 |
| 34 | joda-time 1.6.2 | joda-time 2.12.7 | 버전업 | **기존 코드 유지**. java.time 전환은 점진적으로 권장 |
| 35 | jsoup (신규) | jsoup 1.18.1 | 신규 | nekohtml 대체. HTML 파싱 사실상 표준 |

#### 제거 (프로젝트 폐기 / REST 전환)

| # | ASIS | 제거 사유 | 대체 방안 |
|---|------|----------|----------|
| 36 | JSP | REST API 기반 전환 | @RestController + JSON 응답 |
| 37 | Tiles 2.x | Spring 6에서 지원 완전 제거 | REST 전환으로 불필요 |
| 38 | cos.jar (MultipartRequest) | 프로젝트 미관리, REST 전환 | Spring Multipart |
| 39 | sitemesh | REST 전환으로 불필요 | 불필요 |
| 40 | jxl | 프로젝트 폐기 | POI 5.x |
| 41 | commons-httpclient 3.x | EOL | httpclient5 |

---

## 4. 솔루션/자체 라이브러리

바이너리가 Maven Central에 배포되어 있지 않거나, 소스 코드를 확보할 수 없는 라이브러리이다. 별도 절차를 통해 JDK 17 호환 여부를 확인해야 한다.

| 파일명 | 추정 용도 | 향후 조치 |
|--------|----------|----------|
| ozenc_utf8.jar | 인코딩/보안 모듈 | 담당 부서에 JDK 17 호환 버전 요청 |
| queryapi500.jar / queryapi530.jar | 쿼리 API | 담당 부서에 업데이트 버전 요청 |
| RpsMerge.jar | 리포트 병합 | 벤더사에 JDK 17 호환 확인 |
| rptcertapi.jar | 리포트 인증 | 벤더사에 JDK 17 호환 확인 |
| sciSecu_v2.jar / sciSecuPCC.jar | 보안 모듈 | 보안팀에 JDK 17 호환 확인 |
| seedx.jar | SEED 암호화 | 암호화 모듈 JDK 17 호환 테스트 필요 |
| anyframe-* | 삼성SDS Anyframe 프레임워크 | SDS에 Spring Boot 3 호환 버전 문의 |
| transkey-* | 키보드 보안 | 벤더사에 JDK 17/Jakarta EE 호환 문의 |
| lucy-xss-servlet | Naver Lucy XSS 서블릿 | **2.0.0 Maven Central 배포 확인 완료** |
| ojdbc14 | Oracle JDBC (JDK 1.4용) | H2 인메모리 사용 (Oracle 미사용) |

---

## 5. 주요 결정 사항과 근거

### 5.1 JSP 유지 + WAR 패키징

기존 JSP 기반 화면을 유지한다. Spring Boot 3에서 JSP를 사용하려면 `war` 패키징이 필요하며, `tomcat-embed-jasper`와 JSTL(`jakarta.servlet.jsp.jstl`)을 추가해야 한다. JSTL 태그 URI는 `javax.servlet.jsp.jstl`에서 `jakarta.tags.core`로 변경된다.

### 5.2 Ehcache 3 jakarta classifier

Spring Boot 3은 Jakarta EE 10 기반이므로 Ehcache 3의 `<classifier>jakarta</classifier>` 지정이 필수이다. 누락 시 `javax.cache` 패키지를 찾을 수 없어 `ClassNotFoundException`이 발생한다.

### 5.3 commons-httpclient 3.x 제거 (EOL)

Apache Commons HttpClient 3.x는 완전 EOL(End of Life)로 보안 업데이트가 없다. Apache HttpClient 5.x (`org.apache.httpcomponents.client5:httpclient5`)로 전환이 필수이다. groupId, artifactId, 패키지명이 모두 변경되었다.

### 5.4 commons-collections 3 → 4 필수 전환 (RCE 취약점)

Apache Commons Collections 3.x에는 역직렬화를 통한 원격 코드 실행(RCE) 취약점(CVE-2015-6420)이 존재한다. 패키지가 `org.apache.commons.collections4`로 변경되어 import 수정이 필요하지만, **보안상 전환이 필수**이다.

### 5.5 JDOM 1.x → JDOM2 (artifactId 변경)

JDOM 1.x (`org.jdom:jdom`)는 EOL이며 JDK 17 호환성이 불확실하다. JDOM2 2.0.6.1 (`org.jdom:jdom2`)로 전환 시 artifactId가 변경되므로 의존성 선언을 수정해야 한다.

### 5.6 Log4j 1.x → log4j-1.2-api 브릿지

Log4j 1.x는 CVE-2019-17571 등 심각한 취약점이 존재하여 그대로 사용할 수 없다. 기존 코드에서 `org.apache.log4j.Logger`를 사용하는 경우, **Log4j2의 `log4j-1.2-api` 브릿지**를 통해 API 호환성을 유지하면서 실제 로깅은 Logback(Boot 기본)으로 라우팅할 수 있다.

### 5.7 joda-time 유지 (버전업)

기존 코드에서 `org.joda.time.DateTime` 등을 사용하는 경우, 즉시 `java.time`으로 전환하면 공수가 크다. joda-time 2.12.7은 JDK 17에서 정상 동작하므로 **버전업으로 기존 코드를 유지**하고, `java.time` 전환은 점진적으로 진행한다.

---

## 6. 프로젝트 구조

```
5-homepage/
├── Dockerfile                          # Multi-stage 빌드 (Maven → CentOS 7 + JDK 17)
├── docker-compose.yml                  # 앱 (H2 인메모리, 단독 실행)
├── pom.xml                             # 전체 의존성 정의 (ASIS→TOBE 주석 포함)
├── README.md
└── src/
    ├── main/
    │   ├── java/com/ss/homepage/
    │   │   ├── HomepageApplication.java
    │   │   ├── config/
    │   │   │   ├── CacheConfig.java            # Ehcache 3 JCache
    │   │   │   ├── HttpClientConfig.java        # HttpClient 5 Bean
    │   │   │   └── QuartzConfig.java           # Quartz 스케줄러
    │   │   ├── controller/
    │   │   │   └── HealthCheckController.java  # REST + JSP 뷰
    │   │   ├── entity/
    │   │   │   └── SampleEntity.java           # JPA (Hibernate 6)
    │   │   ├── job/
    │   │   │   └── SampleQuartzJob.java
    │   │   └── repository/
    │   │       └── SampleRepository.java
    │   ├── resources/
    │   │   ├── application.yml                 # H2, 포트 8081
    │   │   ├── ehcache.xml
    │   │   └── logback-spring.xml
    │   └── webapp/WEB-INF/jsp/
    │       ├── index.jsp                       # 메인 페이지 JSP
    │       └── hello.jsp                       # JSP 뷰 (기존 기술 유지 검증)
    └── test/java/com/ss/homepage/
        ├── boot/                               # Spring Boot 3.5.11 컨텍스트
        ├── cache/                              # Ehcache 3.10.8
        ├── commons/                            # BeanUtils, Codec, Collections, Compress, IO, Lang3
        ├── http/                               # HttpClient 5
        ├── office/                             # Apache POI 5.3.0
        ├── removed/                            # 제거 라이브러리 부재 확인
        ├── scheduler/                          # Quartz
        ├── security/                           # AntiSamy, Lucy XSS
        ├── svg/                                # Batik 1.17
        ├── template/                           # Velocity 2.4.1
        ├── util/                               # JodaTime, Gson, jsoup
        └── xml/                                # dom4j, JDOM2, Xerces, XMLBeans
```

---

## 7. 실행 방법

### 로컬 실행 (H2 인메모리 DB)

```bash
cd 5-homepage
mvn spring-boot:run
```

- 애플리케이션: http://localhost:8081
- 헬스체크: http://localhost:8081/health
- H2 콘솔: http://localhost:8081/h2-console (JDBC URL: `jdbc:h2:mem:testdb`)

### Docker 실행

```bash
cd 5-homepage
docker-compose up -d
```

- 애플리케이션: http://localhost:8081

---

## 8. 테스트 실행

```bash
cd 5-homepage
mvn clean test
```

### 테스트 그룹별 검증 대상

| 테스트 그룹 | 검증 대상 라이브러리 |
|------------|-------------------|
| boot/ | Spring Boot 3.5.11 컨텍스트 로딩 |
| cache/ | Ehcache 3.10.8 (JCache, jakarta classifier) |
| commons/ | BeanUtils, Codec, Collections4, Compress, IO, Lang3 |
| http/ | HttpClient 5 (클라이언트 생성, 요청 객체) |
| office/ | Apache POI 5.3.0 (Excel .xlsx 읽기/쓰기) |
| removed/ | 제거 라이브러리 ClassPath 부재 확인 |
| scheduler/ | Quartz 2.3.2+ (Boot 자동 구성) |
| security/ | AntiSamy 1.7.6 (XSS 필터), Lucy XSS Servlet 2.0.0 |
| svg/ | Batik 1.17 (SVG 렌더링) |
| template/ | Velocity 2.4.1 |
| util/ | joda-time 2.12.7, Gson 2.10.1, jsoup 1.18.1 |
| xml/ | dom4j 2.1.4, JDOM2 2.0.6.1, Xerces 2.12.2, XMLBeans 5.2.0 |

---

## 9. 알려진 제약사항

1. **솔루션/자체 라이브러리 미검증**: anyframe, transkey, sciSecu 등 10건은 바이너리 미확보로 본 프로젝트에 포함되지 않았다. 각 담당 부서/벤더사에 JDK 17 호환 버전을 별도 요청해야 한다.

2. **Lucy XSS Servlet 서블릿 필터 완전 검증 불가**: lucy-xss-servlet은 web.xml 기반 서블릿 필터로 설계되어, Spring Boot 단위 테스트에서 완전한 필터 동작 검증이 어렵다. 클래스 로딩 확인만 수행하며, 통합 테스트 환경에서 별도 검증 권장.

3. **CentOS 7 EOL**: Docker 런타임 CentOS 7은 2024년 6월 지원 종료. 운영 환경에서는 Rocky Linux 9 / AlmaLinux 9 권장.

4. **batik과 xercesImpl transitive dependency 충돌 가능성**: `mvn dependency:tree`로 실제 해석 버전을 확인하고, 필요 시 `<exclusions>` 적용.

5. **XMLBeans 5.x와 POI-OOXML 버전 정합성**: POI 5.3.0이 요구하는 XMLBeans 버전과 명시된 5.2.0이 맞지 않을 경우 클래스패스 충돌이 발생할 수 있다. `mvn dependency:tree`로 확인 권장.
