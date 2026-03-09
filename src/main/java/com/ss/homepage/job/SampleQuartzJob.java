package com.ss.homepage.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 샘플 Quartz Job.
 *
 * <p>ASIS: Quartz 1.6.1의 {@code org.quartz.Job} 인터페이스 직접 구현<br>
 * TOBE: Spring의 {@code QuartzJobBean} 상속 (Quartz 2.3.2+, Spring Boot 3 연동)</p>
 *
 * <p>전환 이유: {@code QuartzJobBean}을 상속하면 Spring의 의존성 주입(DI)을
 * Job 내부에서 활용할 수 있으며, Spring Boot의 자동 구성과 자연스럽게 통합된다.</p>
 */
public class SampleQuartzJob extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(SampleQuartzJob.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Job 실행 로직.
     *
     * @param context Quartz 실행 컨텍스트
     * @throws JobExecutionException Job 실행 중 예외 발생 시
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String currentTime = LocalDateTime.now().format(FORMATTER);
        log.info("Quartz Job 실행: {}", currentTime);
    }
}
