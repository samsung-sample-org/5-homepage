package com.ss.homepage.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Joda-Time 라이브러리 테스트.
 *
 * <p>ASIS: joda-time 1.6.2<br>
 * TOBE: joda-time 2.12.7</p>
 *
 * <p>전환 이유: 기존 코드 유지를 위해 버전업. java.time 전환은 점진적으로 권장.</p>
 */
class JodaTimeTest {

    @Test
    @DisplayName("[TOBE] joda-time 2.12.7 - DateTime 생성 및 연산 확인")
    void dateTimeOperations() {
        DateTime now = DateTime.now();
        assertNotNull(now, "DateTime.now()가 null이면 안 된다");

        DateTime tomorrow = now.plusDays(1);
        assertTrue(tomorrow.isAfter(now), "내일은 오늘 이후여야 한다");
    }

    @Test
    @DisplayName("[TOBE] joda-time 2.12.7 - LocalDate 생성 확인")
    void localDateCreation() {
        LocalDate today = LocalDate.now();
        assertNotNull(today, "LocalDate.now()가 null이면 안 된다");
        assertTrue(today.getYear() >= 2024, "연도가 2024 이상이어야 한다");
    }

    @Test
    @DisplayName("[TOBE] joda-time 2.12.7 - DateTimeFormat 포맷팅 확인")
    void dateTimeFormatting() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dt = new DateTime(2026, 1, 1, 0, 0);
        String formatted = fmt.print(dt);
        assertEquals("2026-01-01", formatted, "날짜 포맷이 일치해야 한다");
    }
}
