package com.ss.homepage.commons;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Apache Commons Lang3 테스트.
 *
 * <p>ASIS: commons-lang 2.1 (commons-lang:commons-lang, org.apache.commons.lang)<br>
 * TOBE: commons-lang3 (Boot 관리 버전, 3.14.x, org.apache.commons.lang3)</p>
 *
 * <p>전환 이유: lang 2.x → lang3로 패키지 변경(org.apache.commons.lang → lang3).
 * StringUtils, ObjectUtils 등 API가 개선되었으며,
 * 기존 import 경로를 모두 org.apache.commons.lang3으로 변경해야 한다.</p>
 */
class Lang3Test {

    @Test
    @DisplayName("[TOBE] commons-lang3 Boot 관리 버전 - StringUtils.isBlank 동작 확인")
    void stringUtilsIsBlank() {
        // null, 빈 문자열, 공백만 있는 문자열은 blank로 판별되어야 한다
        assertTrue(StringUtils.isBlank(null), "null은 blank여야 한다");
        assertTrue(StringUtils.isBlank(""), "빈 문자열은 blank여야 한다");
        assertTrue(StringUtils.isBlank("   "), "공백만 있는 문자열은 blank여야 한다");
        assertFalse(StringUtils.isBlank("hello"), "일반 문자열은 blank가 아니어야 한다");
    }

    @Test
    @DisplayName("[TOBE] commons-lang3 Boot 관리 버전 - StringUtils.abbreviate 동작 확인")
    void stringUtilsAbbreviate() {
        // given
        String longText = "이것은 매우 긴 문자열입니다. 일정 길이 이상은 잘라냅니다.";

        // when: 최대 10자로 축약
        String abbreviated = StringUtils.abbreviate(longText, 10);

        // then: 최대 길이 이하이고 '...'으로 끝나야 한다
        assertTrue(abbreviated.length() <= 10, "축약된 문자열은 최대 길이 이하여야 한다");
        assertTrue(abbreviated.endsWith("..."), "축약된 문자열은 '...'으로 끝나야 한다");
    }

    @Test
    @DisplayName("[TOBE] commons-lang3 Boot 관리 버전 - StringUtils.capitalize 동작 확인")
    void stringUtilsCapitalize() {
        assertEquals("Hello", StringUtils.capitalize("hello"), "첫 글자가 대문자로 변환되어야 한다");
        assertEquals("", StringUtils.capitalize(""), "빈 문자열은 그대로 반환되어야 한다");
    }
}
