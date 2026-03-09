package com.ss.homepage.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Lucy XSS Servlet 필터 클래스 로딩 확인 테스트.
 *
 * <p>ASIS: lucy-xss (com.navercorp.lucy:lucy-xss, 구버전)<br>
 * TOBE: lucy-xss-servlet 2.0.0 (com.navercorp.lucy:lucy-xss-servlet)</p>
 *
 * <p>전환 이유: XSS 방어 서블릿 필터로, lucy-xss-servlet이
 * Spring Boot 환경의 서블릿 필터 체인에서 동작하도록 설계되었다.</p>
 *
 * <p>lucy-xss-servlet은 web.xml 기반 설정을 사용하므로 단위 테스트에서
 * 완전한 필터 동작은 검증하지 않고, 클래스 로딩 확인만 수행한다.</p>
 */
class LucyXssTest {

    @Test
    @DisplayName("[TOBE] lucy-xss-servlet 2.0.0 - XssFilter 클래스 로딩 확인")
    void xssFilterClassLoading() {
        // lucy-xss-servlet의 XssFilter 클래스가 ClassPath에 존재하는지 확인한다
        assertDoesNotThrow(() -> {
            Class<?> xssFilterClass = Class.forName("com.navercorp.lucy.security.xss.servletfilter.XssFilter");
            assertNotNull(xssFilterClass, "XssFilter 클래스가 null이면 안 된다");
        }, "XssFilter 클래스 로딩 중 예외가 발생하면 안 된다");
    }

    @Test
    @DisplayName("[TOBE] lucy-xss-servlet 2.0.0 - XssEscapeServletOutputStream 클래스 로딩 확인")
    void xssEscapeServletOutputStreamClassLoading() {
        // lucy-xss-servlet의 핵심 클래스들이 ClassPath에 존재하는지 확인한다
        assertDoesNotThrow(() -> {
            Class.forName("com.navercorp.lucy.security.xss.servletfilter.XssEscapeFilter");
        }, "XssEscapeFilter 클래스 로딩 중 예외가 발생하면 안 된다");
    }
}
