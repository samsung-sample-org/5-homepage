package com.ss.homepage.commons;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Apache Commons IO 테스트.
 *
 * <p>ASIS: commons-io 1.4<br>
 * TOBE: commons-io 2.16.1</p>
 *
 * <p>전환 이유: FileUtils, IOUtils 등 유틸 클래스 대폭 개선, JDK 8+ NIO 지원.
 * 파일 쓰기/읽기 및 InputStream → String 변환이 정상 동작하는지 확인한다.</p>
 */
class IoTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("[TOBE] commons-io 2.16.1 - FileUtils.writeStringToFile 동작 확인")
    void writeStringToFile() throws Exception {
        // given
        File file = tempDir.resolve("test.txt").toFile();
        String content = "안녕하세요, Commons IO 테스트입니다.";

        // when: 파일에 문자열 쓰기
        FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);

        // then: 파일에서 읽은 내용이 동일해야 한다
        String readContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        assertEquals(content, readContent, "파일에 쓴 내용과 읽은 내용이 일치해야 한다");
    }

    @Test
    @DisplayName("[TOBE] commons-io 2.16.1 - IOUtils.toString 동작 확인")
    void ioUtilsToString() throws Exception {
        // given: 파일에 내용 기록
        File file = tempDir.resolve("stream-test.txt").toFile();
        String content = "스트림 테스트 문자열";
        FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);

        // when: InputStream을 String으로 변환
        try (InputStream is = new FileInputStream(file)) {
            String result = IOUtils.toString(is, StandardCharsets.UTF_8);

            // then
            assertEquals(content, result, "IOUtils.toString 결과가 원본과 일치해야 한다");
        }
    }
}
