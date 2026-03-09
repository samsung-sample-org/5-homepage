package com.ss.homepage.svg;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Apache Batik SVG 처리 테스트.
 *
 * <p>ASIS: batik 1.7 (개별 batik-* 모듈)<br>
 * TOBE: batik-all 1.17 (통합 모듈)</p>
 *
 * <p>전환 이유: SVG 렌더링 보안 취약점 다수 패치, SSRF 방어 강화.
 * SAXSVGDocumentFactory를 통해 SVG 문서를 파싱할 수 있는지 확인한다.</p>
 */
class BatikTest {

    private static final String SAMPLE_SVG =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\">"
            + "  <circle cx=\"50\" cy=\"50\" r=\"40\" fill=\"blue\"/>"
            + "</svg>";

    @Test
    @DisplayName("[TOBE] batik 1.17 - SAXSVGDocumentFactory 생성 확인")
    void createSvgDocumentFactory() {
        // SAXSVGDocumentFactory를 생성하여 Batik SVG 파서가 로딩되는지 확인한다
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        assertNotNull(factory, "SAXSVGDocumentFactory가 null이면 안 된다");
    }

    @Test
    @DisplayName("[TOBE] batik 1.17 - SVG Document 파싱 확인")
    void parseSvgDocument() throws Exception {
        // SVG 문자열을 파싱하여 Document 객체를 생성한다
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);

        ByteArrayInputStream input = new ByteArrayInputStream(
                SAMPLE_SVG.getBytes(StandardCharsets.UTF_8));
        Document document = factory.createDocument(
                "http://example.com/test.svg", input);

        assertNotNull(document, "SVG Document가 null이면 안 된다");
        assertEquals("svg", document.getDocumentElement().getLocalName(),
                "루트 엘리먼트는 'svg'여야 한다");
    }
}
