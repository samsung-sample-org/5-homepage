package com.ss.homepage.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Gson JSON 직렬화/역직렬화 테스트.
 *
 * <p>ASIS: 없음 (또는 구버전 Gson)<br>
 * TOBE: gson 2.10.1</p>
 *
 * <p>전환 이유: JSON 처리 라이브러리, 보안 취약점 패치 및 JDK 17 호환.</p>
 */
class JsonTest {

    @Test
    @DisplayName("[TOBE] Gson 2.10.1 - 객체 직렬화 확인")
    void serializeObject() {
        // given
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "홈페이지 시스템");
        jsonObject.addProperty("version", 3);

        // when
        String json = gson.toJson(jsonObject);

        // then
        assertNotNull(json, "JSON 문자열이 null이면 안 된다");
        assertEquals(true, json.contains("홈페이지 시스템"), "JSON에 name 값이 포함되어야 한다");
    }

    @Test
    @DisplayName("[TOBE] Gson 2.10.1 - JSON 문자열 역직렬화 확인")
    void deserializeJson() {
        // given
        Gson gson = new Gson();
        String json = "{\"title\":\"홈페이지\",\"count\":42}";

        // when
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        // then
        assertNotNull(jsonObject, "JsonObject가 null이면 안 된다");
        assertEquals("홈페이지", jsonObject.get("title").getAsString(),
                "title 값이 일치해야 한다");
        assertEquals(42, jsonObject.get("count").getAsInt(),
                "count 값이 일치해야 한다");
    }
}
