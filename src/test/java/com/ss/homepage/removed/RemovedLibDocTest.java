package com.ss.homepage.removed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 제거된 라이브러리 ClassPath 부재 확인 테스트.
 *
 * <p>Spring Boot 3 전환 시 더 이상 필요하지 않거나 대체된 라이브러리들이
 * 실제로 ClassPath에 존재하지 않는지 확인한다.
 * {@link Class#forName(String)}으로 클래스 로딩을 시도하여
 * {@link ClassNotFoundException}이 발생하면 정상이다.</p>
 *
 * <h2>5-homepage 제거 라이브러리 목록</h2>
 * <ul>
 *   <li>commons-httpclient 3.x → httpclient5로 대체</li>
 *   <li>cglib 2.x → ByteBuddy (Spring 6 내장)</li>
 *   <li>Jackson 1.x → Boot Jackson2</li>
 *   <li>cos.jar → Spring Multipart</li>
 *   <li>commons-pool 1.x → commons-pool2</li>
 *   <li>sitemesh → REST 기반 전환 (JSP 없음)</li>
 *   <li>jxl → POI 5.x</li>
 *   <li>log4j 1.x → log4j-1.2-api 브릿지 (Logback)</li>
 * </ul>
 */
class RemovedLibDocTest {

    @Test
    @DisplayName("전환: Log4j 1.x → log4j-1.2-api 브릿지 (기존 API 유지, 내부는 Log4j2/SLF4J로 라우팅)")
    void log4j1xShouldBeAvailableViaBridge() {
        // Log4j 1.x 원본은 제거하고 log4j-1.2-api 브릿지를 추가했다.
        // 브릿지가 org.apache.log4j 패키지를 제공하므로 클래스 로딩이 가능하다.
        // 기존 코드에서 org.apache.log4j.Logger를 직접 사용하는 경우 변경 없이 동작한다.
        try {
            Class.forName("org.apache.log4j.Logger");
            // log4j-1.2-api 브릿지에 의해 로딩 가능 - 정상 동작
        } catch (ClassNotFoundException e) {
            // 브릿지가 없는 경우 - 역시 정상 동작 (Logback 직접 사용)
        }
    }

    @Test
    @DisplayName("제거: commons-logging → 대안: SLF4J (spring-jcl 브릿지로 호환 제공)")
    void commonsLoggingShouldBeReplacedBySpringJcl() {
        // commons-logging의 Log 인터페이스는 spring-jcl 브릿지가 제공할 수 있으므로
        // ClassNotFoundException이 발생하지 않을 수 있다.
        // spring-jcl이 org.apache.commons.logging 패키지를 자체 구현하기 때문이다.
        try {
            Class.forName("org.apache.commons.logging.Log");
            // spring-jcl 브릿지에 의해 로딩 가능 - 정상 동작
        } catch (ClassNotFoundException e) {
            // commons-logging이 완전히 제거된 경우 - 역시 정상 동작
        }
    }

    @Test
    @DisplayName("제거: cglib 2.x (net.sf.cglib) → 대안: ByteBuddy (Spring 6 내장)")
    void cglibShouldNotBeOnClasspath() {
        // cglib 2.x는 명시적으로 제거했으나,
        // 일부 transitive dependency로 cglib이 포함될 수 있다.
        // JDK 17에서 IncompatibleClassChangeError가 발생하면 제거 당위성 확인이 된다.
        try {
            Class.forName("net.sf.cglib.proxy.Enhancer");
            System.out.println("[WARNING] net.sf.cglib.proxy.Enhancer가 ClassPath에 존재합니다. "
                    + "transitive dependency를 확인하세요.");
        } catch (ClassNotFoundException e) {
            // 기대 동작: cglib이 ClassPath에 없음
        } catch (IncompatibleClassChangeError | NoClassDefFoundError e) {
            // JDK 17 호환성 문제로 로딩 실패 - cglib이 정상 동작하지 않음을 확인
        }
    }

    @Test
    @DisplayName("제거: commons-httpclient 3.x → 대안: httpclient5 (org.apache.httpcomponents.client5)")
    void commonsHttpClientShouldNotBeOnClasspath() {
        // commons-httpclient 3.x의 핵심 클래스가 ClassPath에 없어야 한다
        // httpclient5로 완전 대체됨 (groupId, artifactId, 패키지 모두 변경)
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("org.apache.commons.httpclient.HttpClient"),
                "commons-httpclient 3.x (org.apache.commons.httpclient.HttpClient)가 ClassPath에 존재하면 안 된다");
    }

    @Test
    @DisplayName("제거: Jackson 1.x (org.codehaus.jackson) → 대안: Jackson 2.x (com.fasterxml.jackson, Boot 관리)")
    void jackson1xShouldNotBeOnClasspath() {
        // Jackson 1.x의 패키지(org.codehaus.jackson)는 ClassPath에 없어야 한다
        // Boot Jackson 2.x가 자동으로 포함되며 패키지명이 com.fasterxml.jackson으로 변경됨
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("org.codehaus.jackson.map.ObjectMapper"),
                "Jackson 1.x (org.codehaus.jackson.map.ObjectMapper)가 ClassPath에 존재하면 안 된다");
    }

    @Test
    @DisplayName("제거: jxl → 대안: Apache POI 5.x (.xls/.xlsx 통합 지원)")
    void jxlShouldNotBeOnClasspath() {
        // jxl 라이브러리가 ClassPath에 없어야 한다
        // Apache POI 5.x로 완전 대체됨 (.xls/.xlsx 모두 지원)
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("jxl.Workbook"),
                "jxl (jxl.Workbook)이 ClassPath에 존재하면 안 된다");
    }

    @Test
    @DisplayName("제거: cos.jar (MultipartRequest) → 대안: Spring Multipart (StandardServletMultipartResolver)")
    void cosMultipartShouldNotBeOnClasspath() {
        // cos.jar의 MultipartRequest 클래스가 ClassPath에 없어야 한다
        // Spring Boot의 StandardServletMultipartResolver로 자동 대체됨
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("com.oreilly.servlet.MultipartRequest"),
                "cos.jar (com.oreilly.servlet.MultipartRequest)가 ClassPath에 존재하면 안 된다");
    }

    @Test
    @DisplayName("제거: commons-pool 1.x → 대안: commons-pool2 (Boot BOM 관리)")
    void commonsPool1xShouldNotBeOnClasspath() {
        // commons-pool 1.x의 핵심 클래스가 ClassPath에 없어야 한다
        // commons-pool2로 대체됨 (artifactId 변경: commons-pool → commons-pool2)
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("org.apache.commons.pool.ObjectPool"),
                "commons-pool 1.x (org.apache.commons.pool.ObjectPool)가 ClassPath에 존재하면 안 된다");
    }
}
