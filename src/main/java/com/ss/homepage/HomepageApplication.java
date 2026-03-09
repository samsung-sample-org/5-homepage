package com.ss.homepage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 홈페이지 애플리케이션 진입점.
 *
 * <p>본 프로젝트는 JDK 17 + Spring Boot 3 환경에서
 * 기존(ASIS) 라이브러리들의 TOBE 전환 호환성을 검증하기 위한 샘플 애플리케이션이다.</p>
 *
 * <p>검증 대상 라이브러리:</p>
 * <ul>
 *   <li>Spring Boot 3.5.11 (Spring 6.1+, Jakarta EE 10)</li>
 *   <li>Ehcache 3.10.8 (javax → jakarta, JCache/JSR-107 표준)</li>
 *   <li>Apache HttpClient 5.x (org.apache.http → org.apache.hc)</li>
 *   <li>Quartz Scheduler 2.3.2+ (spring-boot-starter-quartz)</li>
 *   <li>AntiSamy 1.7.6 (XSS 필터, CVE 패치)</li>
 *   <li>Lucy XSS Servlet 2.0.0 (Naver Lucy XSS 서블릿 필터)</li>
 *   <li>Apache POI 5.3.0 + poi-ooxml (Excel .xlsx 지원)</li>
 *   <li>Velocity Engine Core 2.4.1 (velocity 1.7 → artifactId 변경)</li>
 *   <li>Batik 1.17 (SVG 렌더링, SSRF 방어 강화)</li>
 *   <li>dom4j 2.1.4 (dom4j:dom4j → org.dom4j:dom4j, XXE 패치)</li>
 *   <li>JDOM2 2.0.6.1 (jdom 1.x → jdom2, JDK 17 호환)</li>
 *   <li>Xerces 2.12.2 (XML 파싱 보안 패치)</li>
 *   <li>XMLBeans 5.2.0 (POI-OOXML XML 처리)</li>
 *   <li>commons-collections4 4.5.0 (RCE CVE-2015-6420 패치)</li>
 *   <li>commons-beanutils 1.9.4 (CVE-2014-0114 패치)</li>
 *   <li>commons-io 2.16.1 (JDK 8+ NIO 지원)</li>
 *   <li>commons-compress 1.26.1 (경로 순회 취약점 패치)</li>
 *   <li>commons-math3 3.6.1 (수학/통계 연산)</li>
 *   <li>Gson 2.10.1 (JSON 직렬화/역직렬화)</li>
 *   <li>jsoup 1.18.1 (nekohtml 대체, HTML 파싱)</li>
 *   <li>joda-time 2.12.7 (기존 코드 유지, java.time 점진적 전환)</li>
 *   <li>jakarta.activation-api (javax.activation → jakarta, JDK 17 필수)</li>
 *   <li>log4j-1.2-api + log4j-core (Log4j 1.x EOL 브릿지)</li>
 * </ul>
 *
 * @author SS Sample
 */
@SpringBootApplication
public class HomepageApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomepageApplication.class, args);
    }
}
