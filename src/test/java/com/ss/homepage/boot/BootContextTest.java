package com.ss.homepage.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Spring Boot 컨텍스트 로딩 테스트.
 *
 * <p>ASIS: Spring Framework 3.x (XML 기반 컨텍스트 수동 구성)<br>
 * TOBE: Spring Boot 3.5.11 (Spring Framework 6.1+, 자동 구성)</p>
 *
 * <p>전환 이유: Spring 5 이하 EOL. Boot 3.x는 Jakarta EE 10 기반으로
 * 자동 설정(@SpringBootTest)을 통해 컨텍스트 로딩을 검증할 수 있다.</p>
 */
@SpringBootTest
class BootContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("[TOBE] Spring Boot 3.5.11 - ApplicationContext 로딩 확인")
    void contextLoads() {
        // Spring Boot 3.5.11 (Spring 6.1+) 컨텍스트가 정상적으로 로딩되는지 확인
        assertNotNull(applicationContext, "ApplicationContext가 null이면 안 된다");
    }
}
