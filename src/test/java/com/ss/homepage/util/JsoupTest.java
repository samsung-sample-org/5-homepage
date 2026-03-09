package com.ss.homepage.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * jsoup HTML 파싱 테스트.
 *
 * <p>ASIS: nekohtml 1.x (HTML 파싱)<br>
 * TOBE: jsoup 1.18.1</p>
 *
 * <p>전환 이유: NekoHTML EOL, Jsoup이 사실상 표준 HTML 파서로 자리잡음.
 * HTML 문자열을 파싱하고 CSS 선택자로 요소를 추출할 수 있는지 확인한다.</p>
 */
class JsoupTest {

    @Test
    @DisplayName("[TOBE] jsoup 1.18.1 - HTML 파싱 확인")
    void parseHtml() {
        // HTML 문자열을 파싱하여 Document 객체를 생성한다
        String html = "<html><body><h1>홈페이지 시스템</h1><p class='desc'>JDK 17 마이그레이션</p></body></html>";
        Document document = Jsoup.parse(html);

        assertNotNull(document, "Document가 null이면 안 된다");
        assertEquals("홈페이지 시스템", document.select("h1").first().text(),
                "h1 텍스트가 일치해야 한다");
    }

    @Test
    @DisplayName("[TOBE] jsoup 1.18.1 - CSS 선택자로 요소 추출 확인")
    void selectElements() {
        // CSS 선택자를 사용하여 특정 요소를 추출한다
        String html = "<ul><li class='item'>항목1</li><li class='item'>항목2</li><li>항목3</li></ul>";
        Document document = Jsoup.parse(html);

        Elements items = document.select("li.item");
        assertEquals(2, items.size(), "class='item'인 li 요소가 2개여야 한다");
        assertEquals("항목1", items.get(0).text(), "첫 번째 항목 텍스트가 일치해야 한다");
        assertEquals("항목2", items.get(1).text(), "두 번째 항목 텍스트가 일치해야 한다");
    }

    @Test
    @DisplayName("[TOBE] jsoup 1.18.1 - 속성 추출 확인")
    void extractAttribute() {
        // href 속성이 있는 a 태그에서 URL을 추출한다
        String html = "<a href='https://example.com/homepage' id='mainLink'>홈페이지 바로가기</a>";
        Document document = Jsoup.parse(html);

        Element link = document.select("a#mainLink").first();
        assertNotNull(link, "링크 요소가 존재해야 한다");
        assertEquals("https://example.com/homepage", link.attr("href"),
                "href 속성 값이 일치해야 한다");
        assertEquals("홈페이지 바로가기", link.text(),
                "링크 텍스트가 일치해야 한다");
    }
}
