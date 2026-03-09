package com.ss.homepage.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * dom4j XML 처리 테스트.
 *
 * <p>ASIS: dom4j 1.6.1 (dom4j:dom4j)<br>
 * TOBE: dom4j 2.1.4 (org.dom4j:dom4j)</p>
 *
 * <p>전환 이유: dom4j 1.x에 XXE 취약점 존재, 2.x에서 groupId가 변경되고
 * 보안 패치가 적용되었다.</p>
 */
class Dom4jTest {

    @Test
    @DisplayName("[TOBE] dom4j 2.1.4 - Document 생성 확인")
    void createDocument() {
        // dom4j 2.x DocumentHelper를 통해 빈 Document를 생성한다
        Document document = DocumentHelper.createDocument();
        assertNotNull(document, "Document 객체가 null이면 안 된다");
    }

    @Test
    @DisplayName("[TOBE] dom4j 2.1.4 - Element 추가 확인")
    void addElement() {
        // Document에 루트 Element와 자식 Element를 추가한다
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("homepage");
        root.addAttribute("name", "홈페이지시스템");

        Element menu = root.addElement("menu");
        menu.setText("메인메뉴");

        assertEquals("homepage", document.getRootElement().getName(),
                "루트 엘리먼트 이름은 'homepage'여야 한다");
        assertEquals("홈페이지시스템", root.attributeValue("name"),
                "루트 엘리먼트의 name 속성이 일치해야 한다");
        assertEquals("메인메뉴", menu.getText(),
                "자식 엘리먼트의 텍스트가 일치해야 한다");
    }

    @Test
    @DisplayName("[TOBE] dom4j 2.1.4 - XML 문자열 출력 확인")
    void documentToXmlString() {
        // Document를 XML 문자열로 변환하여 출력 형식을 검증한다
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");
        root.addElement("child").setText("값");

        String xml = document.asXML();
        assertNotNull(xml, "XML 문자열이 null이면 안 된다");
        assertTrue(xml.contains("<root>"), "XML에 <root> 태그가 포함되어야 한다");
        assertTrue(xml.contains("<child>값</child>"),
                "XML에 <child>값</child> 내용이 포함되어야 한다");
    }
}
