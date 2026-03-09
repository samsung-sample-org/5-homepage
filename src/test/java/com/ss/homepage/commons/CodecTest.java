package com.ss.homepage.commons;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Apache Commons Codec 테스트.
 *
 * <p>ASIS: commons-codec 1.3<br>
 * TOBE: commons-codec (Boot 관리 버전, 1.16.x)</p>
 *
 * <p>전환 이유: Base64, Hex, DigestUtils 등 인코딩 유틸 최신화.
 * SHA-256 해시 및 Base64 인코딩/디코딩이 정상 동작하는지 확인한다.</p>
 */
class CodecTest {

    @Test
    @DisplayName("[TOBE] commons-codec Boot 관리 버전 - SHA-256 해시 생성 확인")
    void sha256Hex() {
        // given
        String input = "hello";

        // when: SHA-256 해시 생성
        String hash = DigestUtils.sha256Hex(input);

        // then: 해시 값이 null이 아니고, 64자(256bit hex)여야 한다
        assertNotNull(hash, "해시 값이 null이면 안 된다");
        assertEquals(64, hash.length(), "SHA-256 해시는 64자(hex)여야 한다");
    }

    @Test
    @DisplayName("[TOBE] commons-codec Boot 관리 버전 - Base64 인코딩/디코딩 확인")
    void base64EncodeDecode() {
        // given
        String original = "테스트 문자열";
        byte[] originalBytes = original.getBytes(StandardCharsets.UTF_8);

        // when: Base64 인코딩 후 디코딩
        byte[] encoded = Base64.encodeBase64(originalBytes);
        byte[] decoded = Base64.decodeBase64(encoded);
        String result = new String(decoded, StandardCharsets.UTF_8);

        // then: 원본과 동일해야 한다
        assertEquals(original, result, "Base64 인코딩/디코딩 후 원본과 동일해야 한다");
    }
}
