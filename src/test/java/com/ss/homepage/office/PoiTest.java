package com.ss.homepage.office;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Apache POI Excel 처리 테스트.
 *
 * <p>ASIS: poi 3.x<br>
 * TOBE: poi 5.3.0 + poi-ooxml 5.3.0</p>
 *
 * <p>전환 이유: Excel HSSF/XSSF 처리 성능 개선, XXE 취약점 패치,
 * JDK 17 호환. XSSFWorkbook으로 .xlsx 파일을 생성하고
 * ByteArrayOutputStream으로 저장할 수 있는지 확인한다.</p>
 */
class PoiTest {

    @Test
    @DisplayName("[TOBE] poi 5.3.0 - XSSFWorkbook 생성 및 Sheet/Row/Cell 작성 확인")
    void createWorkbookWithData() throws Exception {
        // XSSFWorkbook을 생성하고 Sheet, Row, Cell에 데이터를 기록한다
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("테스트시트");
            assertNotNull(sheet, "Sheet가 null이면 안 된다");

            // 헤더 행
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("이름");
            headerRow.createCell(1).setCellValue("부서");
            headerRow.createCell(2).setCellValue("연차");

            // 데이터 행
            XSSFRow dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue("홍길동");
            dataRow.createCell(1).setCellValue("개발팀");
            dataRow.createCell(2).setCellValue(5);

            // 데이터 검증
            XSSFCell nameCell = sheet.getRow(1).getCell(0);
            assertEquals("홍길동", nameCell.getStringCellValue(),
                    "이름 셀 값이 일치해야 한다");

            XSSFCell yearCell = sheet.getRow(1).getCell(2);
            assertEquals(5.0, yearCell.getNumericCellValue(),
                    "연차 셀 값이 일치해야 한다");
        }
    }

    @Test
    @DisplayName("[TOBE] poi 5.3.0 - ByteArrayOutputStream으로 저장 확인")
    void saveToOutputStream() throws Exception {
        // Workbook을 ByteArrayOutputStream에 기록하여 바이트 배열을 생성한다
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("출력테스트");
            sheet.createRow(0).createCell(0).setCellValue("테스트 데이터");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            byte[] bytes = outputStream.toByteArray();
            assertTrue(bytes.length > 0,
                    "출력된 바이트 배열의 크기가 0보다 커야 한다");
        }
    }
}
