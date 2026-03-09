package com.ss.homepage.xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JDOM2 XML 처리 테스트.
 *
 * <p>ASIS: jdom 1.x (org.jdom:jdom)<br>
 * TOBE: jdom2 2.0.6.1 (org.jdom:jdom2)</p>
 *
 * <p>전환 이유: JDOM 1.x는 EOL이며, 2.x에서 artifactId가 jdom2로 변경되고
 * JDK 17 호환성이 개선되었다. Document/Element를 생성하고
 * XML 문자열로 출력할 수 있는지 확인한다.</p>
 */
class JdomTest {

    @Test
    @DisplayName("[TOBE] jdom2 2.0.6.1 - Document 및 Element 생성 확인")
    void createDocumentAndElement() {
        // JDOM2 Document와 루트 Element를 생성한다
        Element root = new Element("homepage");
        Document document = new Document(root);

        assertNotNull(document, "Document가 null이면 안 된다");
        assertEquals("homepage", document.getRootElement().getName(),
                "루트 엘리먼트 이름이 일치해야 한다");
    }

    @Test
    @DisplayName("[TOBE] jdom2 2.0.6.1 - 자식 Element 추가 및 텍스트 설정 확인")
    void addChildElement() {
        // 루트 Element에 자식 Element를 추가하고 텍스트를 설정한다
        Element root = new Element("homepage");
        new Document(root);

        Element titleElement = new Element("title");
        titleElement.setText("홈페이지 시스템");
        root.addContent(titleElement);

        Element pageElement = new Element("page");
        pageElement.setAttribute("id", "main");
        pageElement.setText("메인 페이지");
        root.addContent(pageElement);

        assertEquals("홈페이지 시스템", root.getChild("title").getText(),
                "title 엘리먼트의 텍스트가 일치해야 한다");
        assertEquals("main", root.getChild("page").getAttributeValue("id"),
                "page 엘리먼트의 id 속성이 일치해야 한다");
    }

    @Test
    @DisplayName("[TOBE] jdom2 2.0.6.1 - XMLOutputter로 XML 문자열 출력 확인")
    void outputXmlString() {
        // Document를 XMLOutputter로 문자열로 변환한다
        Element root = new Element("root");
        Document document = new Document(root);
        root.addContent(new Element("child").setText("테스트값"));

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        String xml = outputter.outputString(document);

        assertNotNull(xml, "XML 문자열이 null이면 안 된다");
        assertTrue(xml.contains("<root>"), "XML에 <root> 태그가 포함되어야 한다");
        assertTrue(xml.contains("테스트값"), "XML에 텍스트 값이 포함되어야 한다");
    }
}
