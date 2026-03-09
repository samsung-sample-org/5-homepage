package com.ss.homepage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 헬스체크 및 루트 엔드포인트 컨트롤러.
 *
 * <p>ASIS: Spring MVC 4.x (javax.servlet 기반)<br>
 * TOBE: Spring MVC 6.x (jakarta.servlet 기반, Spring Boot 3)</p>
 *
 * <p>REST 기반 프로젝트이므로 JSP 뷰 없이 JSON/텍스트로만 응답한다.
 * {@code @RestController}를 사용하여 모든 엔드포인트가 응답 바디를 직접 반환한다.</p>
 */
@RestController
public class HealthCheckController {

    /**
     * 헬스체크 엔드포인트.
     *
     * @return "OK" 문자열 (텍스트 응답)
     */
    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    /**
     * 루트 엔드포인트 - 애플리케이션 정보 반환.
     *
     * @return 애플리케이션 정보 맵 (JSON 응답)
     */
    @GetMapping("/")
    public Map<String, Object> index() {
        return Map.of(
                "application", "홈페이지 시스템 - JDK 17 + Boot 3 라이브러리 호환성 검증",
                "springBootVersion", "3.5.11",
                "javaVersion", System.getProperty("java.version"),
                "timestamp", LocalDateTime.now().toString()
        );
    }
}
