package com.ss.homepage.xml;

import org.apache.xmlbeans.XmlObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Apache XMLBeans 테스트.
 *
 * <p>ASIS: 없음 (또는 구버전, POI 내부 의존)<br>
 * TOBE: xmlbeans 5.2.0 (org.apache.xmlbeans:xmlbeans)</p>
 *
 * <p>전환 이유: POI-OOXML의 XML 처리 의존성으로 명시적 버전 관리.
 * XmlObject.Factory를 통한 XML 파싱이 정상 동작하는지 확인한다.</p>
 */
class XmlbeansTest {

    @Test
    @DisplayName("[TOBE] xmlbeans 5.2.0 - XmlObject 파싱 확인")
    void parseXmlObject() throws Exception {
        // XmlObject.Factory.parse를 통해 XML 문자열을 파싱한다
        String xml = "<root><child>test</child></root>";
        XmlObject xmlObject = XmlObject.Factory.parse(xml);

        assertNotNull(xmlObject, "XmlObject가 null이면 안 된다");
    }

    @Test
    @DisplayName("[TOBE] xmlbeans 5.2.0 - XmlObject 클래스 로딩 확인")
    void xmlbeansClassLoading() {
        // XmlObject 클래스가 ClassPath에 존재하는지 확인한다
        assertNotNull(XmlObject.class, "XmlObject 클래스가 null이면 안 된다");
    }
}
