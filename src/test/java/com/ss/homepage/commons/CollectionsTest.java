package com.ss.homepage.commons;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Apache Commons Collections4 테스트.
 *
 * <p>ASIS: commons-collections 3.2 (org.apache.commons.collections)
 *   - RCE 역직렬화 취약점 CVE-2015-6420 존재<br>
 * TOBE: commons-collections4 4.5.0 (org.apache.commons.collections4)</p>
 *
 * <p>전환 이유: commons-collections 3.x에 원격 코드 실행(RCE) 역직렬화 취약점이 존재하여
 * 4.x로 필수 전환. groupId/artifactId 및 패키지명이 모두 변경되었다
 * (org.apache.commons.collections → org.apache.commons.collections4).</p>
 */
class CollectionsTest {

    @Test
    @DisplayName("[TOBE] commons-collections4 4.5.0 - CollectionUtils.isNotEmpty 동작 확인")
    void collectionUtilsIsNotEmpty() {
        // given
        var nonEmptyList = Arrays.asList("a", "b", "c");
        var emptyList = Collections.emptyList();

        // when & then
        assertTrue(CollectionUtils.isNotEmpty(nonEmptyList),
                "비어 있지 않은 리스트는 isNotEmpty가 true여야 한다");
        assertFalse(CollectionUtils.isNotEmpty(emptyList),
                "빈 리스트는 isNotEmpty가 false여야 한다");
    }

    @Test
    @DisplayName("[TOBE] commons-collections4 4.5.0 - MapUtils 동작 확인")
    void mapUtils() {
        // given
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        // when & then
        assertTrue(MapUtils.isNotEmpty(map),
                "비어 있지 않은 맵은 isNotEmpty가 true여야 한다");
        assertEquals("value", MapUtils.getString(map, "key"),
                "key에 해당하는 값을 가져올 수 있어야 한다");
        assertEquals("기본값", MapUtils.getString(map, "없는키", "기본값"),
                "존재하지 않는 키는 기본값을 반환해야 한다");
    }
}
