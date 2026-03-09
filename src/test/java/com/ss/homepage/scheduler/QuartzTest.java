package com.ss.homepage.scheduler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Quartz 스케줄러 통합 테스트.
 *
 * <p>ASIS: quartz 1.6.1 (독립 설정, quartz.properties 직접 관리)<br>
 * TOBE: spring-boot-starter-quartz (Quartz 2.3.2+, Spring Boot 자동 구성)</p>
 *
 * <p>전환 이유: Quartz 1.x는 EOL이며 Spring Boot 3과 호환되지 않는다.
 * spring-boot-starter-quartz는 {@code SchedulerFactoryBean}을 자동 등록하고,
 * {@code @Bean}으로 등록된 JobDetail/Trigger를 자동으로 스케줄러에 등록한다.</p>
 *
 * <p>Spring Boot가 자동 구성한 Scheduler가 정상적으로 주입되고
 * started 상태인지 확인한다.</p>
 */
@SpringBootTest
class QuartzTest {

    @Autowired
    private Scheduler scheduler;

    @Test
    @DisplayName("[TOBE] spring-boot-starter-quartz - Scheduler 주입 확인")
    void schedulerInjected() {
        // Spring Boot가 자동 구성한 Quartz Scheduler가 주입되는지 확인한다
        assertNotNull(scheduler, "Scheduler가 null이면 안 된다");
    }

    @Test
    @DisplayName("[TOBE] spring-boot-starter-quartz - Scheduler started 상태 확인")
    void schedulerIsStarted() throws Exception {
        // Scheduler가 started 상태인지 확인한다
        assertTrue(scheduler.isStarted(),
                "Scheduler가 started 상태여야 한다");
    }
}
