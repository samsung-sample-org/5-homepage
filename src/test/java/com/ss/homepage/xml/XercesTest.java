package com.ss.homepage.xml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Xerces XML 파싱 테스트.
 *
 * <p>ASIS: xercesImpl 2.11.0<br>
 * TOBE: xercesImpl 2.12.2</p>
 *
 * <p>전환 이유: XML 파싱 보안 패치 적용, XXE 방어 기본값 강화.
 * DocumentBuilderFactory를 통한 XML 파싱이 정상 동작하는지 확인한다.</p>
 */
class XercesTest {

    private static final String SAMPLE_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<homepage>"
            + "  <title>홈페이지 시스템</title>"
            + "  <version>3</version>"
            + "</homepage>";

    @Test
    @DisplayName("[TOBE] xercesImpl 2.12.2 - DocumentBuilderFactory 생성 확인")
    void createDocumentBuilderFactory() {
        // DocumentBuilderFactory 인스턴스가 정상 생성되는지 확인한다
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        assertNotNull(factory, "DocumentBuilderFactory가 null이면 안 된다");
    }

    @Test
    @DisplayName("[TOBE] xercesImpl 2.12.2 - XML 파싱 확인")
    void parseXml() throws Exception {
        // XML 문자열을 파싱하여 DOM Document로 변환한다
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        ByteArrayInputStream input = new ByteArrayInputStream(
                SAMPLE_XML.getBytes(StandardCharsets.UTF_8));
        Document document = builder.parse(input);
        assertNotNull(document, "파싱된 Document가 null이면 안 된다");

        Element root = document.getDocumentElement();
        assertEquals("homepage", root.getTagName(),
                "루트 엘리먼트 이름은 'homepage'여야 한다");

        String title = root.getElementsByTagName("title").item(0).getTextContent();
        assertEquals("홈페이지 시스템", title,
                "title 엘리먼트의 텍스트가 일치해야 한다");

        String version = root.getElementsByTagName("version").item(0).getTextContent();
        assertEquals("3", version,
                "version 엘리먼트의 텍스트가 일치해야 한다");
    }
}
