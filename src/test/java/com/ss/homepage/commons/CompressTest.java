package com.ss.homepage.commons;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Apache Commons Compress 테스트.
 *
 * <p>ASIS: 없음 (또는 구버전)<br>
 * TOBE: commons-compress 1.26.1</p>
 *
 * <p>전환 이유: ZIP/TAR/GZ 처리 보안 강화, 경로 순회(Path Traversal) 취약점 패치.
 * ZipArchiveOutputStream을 통해 ZIP 파일을 생성하고
 * 엔트리를 추가할 수 있는지 확인한다.</p>
 */
class CompressTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("[TOBE] commons-compress 1.26.1 - ZipArchiveOutputStream 생성 확인")
    void createZipOutputStream() throws Exception {
        // ZipArchiveOutputStream을 ByteArrayOutputStream에 연결하여 생성한다
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(bos)) {
            assertNotNull(zos, "ZipArchiveOutputStream이 null이면 안 된다");

            // ZIP 엔트리 추가
            ZipArchiveEntry entry = new ZipArchiveEntry("test.txt");
            byte[] content = "테스트 파일 내용".getBytes(StandardCharsets.UTF_8);
            entry.setSize(content.length);
            zos.putArchiveEntry(entry);
            zos.write(content);
            zos.closeArchiveEntry();
        }

        // 생성된 ZIP 바이트가 존재해야 한다
        byte[] zipBytes = bos.toByteArray();
        assertTrue(zipBytes.length > 0, "ZIP 파일 바이트가 0보다 커야 한다");
    }

    @Test
    @DisplayName("[TOBE] commons-compress 1.26.1 - ZIP 파일 생성 및 저장 확인")
    void writeZipFile() throws Exception {
        // 임시 디렉토리에 ZIP 파일을 생성한다
        File zipFile = tempDir.resolve("output.zip").toFile();

        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new FileOutputStream(zipFile))) {
            String[] fileNames = {"file1.txt", "file2.txt"};
            for (String fileName : fileNames) {
                ZipArchiveEntry entry = new ZipArchiveEntry(fileName);
                byte[] content = (fileName + " 내용").getBytes(StandardCharsets.UTF_8);
                entry.setSize(content.length);
                zos.putArchiveEntry(entry);
                zos.write(content);
                zos.closeArchiveEntry();
            }
        }

        assertTrue(zipFile.exists(), "ZIP 파일이 생성되어야 한다");
        assertTrue(zipFile.length() > 0, "ZIP 파일 크기가 0보다 커야 한다");
    }
}
