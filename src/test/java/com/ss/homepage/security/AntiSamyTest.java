package com.ss.homepage.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * AntiSamy XSS 필터링 테스트.
 *
 * <p>ASIS: antisamy 1.4.4<br>
 * TOBE: antisamy 1.7.6</p>
 *
 * <p>전환 이유: XSS 방어 정책 파일 기반 HTML 필터링 라이브러리로,
 * 다수의 CVE 보안 패치가 적용되었다. 악성 HTML 입력이 정상적으로
 * 필터링되는지 확인한다.</p>
 */
class AntiSamyTest {

    @Test
    @DisplayName("[TOBE] antisamy 1.7.6 - AntiSamy 객체 및 Policy 생성 확인")
    void createAntiSamyAndPolicy() throws Exception {
        // AntiSamy 내장 정책(antisamy-slashdot.xml)을 로딩하여 인스턴스를 생성한다
        Policy policy = Policy.getInstance(
                AntiSamy.class.getResourceAsStream("/antisamy-slashdot.xml"));
        assertNotNull(policy, "Policy가 null이면 안 된다");

        AntiSamy antiSamy = new AntiSamy(policy);
        assertNotNull(antiSamy, "AntiSamy 객체가 null이면 안 된다");
    }

    @Test
    @DisplayName("[TOBE] antisamy 1.7.6 - 악성 스크립트 필터링 확인")
    void filterMaliciousScript() throws Exception {
        // <script> 태그가 포함된 악성 HTML이 필터링되는지 확인한다
        Policy policy = Policy.getInstance(
                AntiSamy.class.getResourceAsStream("/antisamy-slashdot.xml"));
        AntiSamy antiSamy = new AntiSamy(policy);

        String maliciousHtml = "<p>안녕하세요</p><script>alert('xss')</script>";
        CleanResults results = antiSamy.scan(maliciousHtml);
        String cleanHtml = results.getCleanHTML();

        assertNotNull(cleanHtml, "정제된 HTML이 null이면 안 된다");
        assertFalse(cleanHtml.contains("<script>"),
                "정제된 HTML에 <script> 태그가 포함되면 안 된다");
        assertFalse(cleanHtml.contains("alert("),
                "정제된 HTML에 alert() 호출이 포함되면 안 된다");
    }
}
