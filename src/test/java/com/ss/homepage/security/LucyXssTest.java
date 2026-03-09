package com.ss.homepage.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
 * <p>lucy-xss-servlet은 설정 파일(lucy-xss-servlet-filter-rule.xml) 기반으로 동작하며,
 * XssEscapeFilter/XssEscapeServletFilter 클래스 초기화 시 해당 파일을 필요로 한다.
 * 단위 테스트에서는 설정 파일 의존이 없는 인터페이스/설정규칙 클래스의
 * ClassPath 존재 여부만 확인한다.</p>
 */
class LucyXssTest {

    @Test
    @DisplayName("[TOBE] lucy-xss-servlet 2.0.0 - XssEscapeFilterConfig 클래스 로딩 확인")
    void xssEscapeFilterConfigClassLoading() throws Exception {
        // lucy-xss-servlet의 설정 클래스가 ClassPath에 존재하는지 확인한다
        // XssEscapeFilter는 초기화 시 설정 파일을 필요로 하므로 Config 클래스로 확인
        Class<?> configClass = Class.forName(
                "com.navercorp.lucy.security.xss.servletfilter.XssEscapeFilterConfig");
        assertNotNull(configClass, "XssEscapeFilterConfig 클래스가 null이면 안 된다");
    }

    @Test
    @DisplayName("[TOBE] lucy-xss-servlet 2.0.0 - XssEscapeFilterRule 클래스 로딩 확인")
    void xssEscapeFilterRuleClassLoading() throws Exception {
        // lucy-xss-servlet의 XssEscapeFilterRule 클래스가 ClassPath에 존재하는지 확인한다
        // 설정 파일 없이 클래스 로딩만 확인 (static initializer 없음)
        Class<?> ruleClass = Class.forName(
                "com.navercorp.lucy.security.xss.servletfilter.XssEscapeFilterRule");
        assertNotNull(ruleClass, "XssEscapeFilterRule 클래스가 null이면 안 된다");
    }
}
