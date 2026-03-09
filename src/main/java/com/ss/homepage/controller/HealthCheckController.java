package com.ss.homepage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * 헬스체크 및 메인 페이지 컨트롤러.
 *
 * <p>ASIS: Spring MVC 4.x (javax.servlet 기반)<br>
 * TOBE: Spring MVC 6.x (jakarta.servlet 기반, Spring Boot 3)</p>
 *
 * <p>{@code @Controller}를 사용하되, 헬스체크 엔드포인트에만
 * {@code @ResponseBody}를 적용하여 JSON/텍스트 응답과 JSP 뷰 반환을 하나의 클래스에서 처리한다.</p>
 */
@Controller
public class HealthCheckController {

    /**
     * 헬스체크 엔드포인트.
     *
     * @return "OK" 문자열 (텍스트 응답)
     */
    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "OK";
    }

    /**
     * 메인 페이지 (JSP index 뷰).
     *
     * @param model JSP에 전달할 모델 객체
     * @return index 뷰 이름
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "홈페이지 샘플 애플리케이션 - JDK 17 + Boot 3 라이브러리 호환성 검증");
        return "index";
    }

    /**
     * JSP 뷰 엔드포인트 (기존 JSP 기술 유지 검증).
     *
     * @param model JSP에 전달할 모델 객체
     * @return hello JSP 뷰 이름
     */
    @GetMapping("/jsp/hello")
    public String jspHello(Model model) {
        model.addAttribute("message", "JSP 동작 확인 - JDK 17 + Spring Boot 3");
        model.addAttribute("now", LocalDateTime.now().toString());
        return "hello";
    }
}
