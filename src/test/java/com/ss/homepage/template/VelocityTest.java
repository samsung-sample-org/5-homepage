package com.ss.homepage.template;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Apache Velocity 템플릿 엔진 테스트.
 *
 * <p>ASIS: velocity 1.7 (org.apache.velocity:velocity)<br>
 * TOBE: velocity-engine-core 2.4.1 (org.apache.velocity:velocity-engine-core)</p>
 *
 * <p>전환 이유: Velocity 1.x는 EOL이며, 2.x에서 artifactId가 변경되었다.
 * Spring Boot 3에는 Velocity auto-config이 없으므로 수동으로 VelocityEngine을
 * 초기화하여 사용해야 한다.</p>
 *
 * <p>VelocityEngine을 직접 초기화하고, 문자열 템플릿을 렌더링하여
 * 변수 치환이 정상 동작하는지 확인한다.</p>
 */
class VelocityTest {

    @Test
    @DisplayName("[TOBE] velocity-engine-core 2.4.1 - VelocityEngine 초기화 확인")
    void initVelocityEngine() {
        // VelocityEngine을 생성하고 초기화한다
        VelocityEngine engine = new VelocityEngine();
        engine.init();
        assertNotNull(engine, "VelocityEngine이 null이면 안 된다");
    }

    @Test
    @DisplayName("[TOBE] velocity-engine-core 2.4.1 - 템플릿 렌더링 확인")
    void renderTemplate() {
        // 문자열 템플릿에 변수를 바인딩하고 StringWriter로 렌더링한다
        VelocityEngine engine = new VelocityEngine();
        engine.init();

        String template = "안녕하세요, ${name}님. ${project} 프로젝트에 오신 것을 환영합니다.";

        VelocityContext context = new VelocityContext();
        context.put("name", "홍길동");
        context.put("project", "홈페이지");

        StringWriter writer = new StringWriter();
        engine.evaluate(context, writer, "test-template", template);

        String result = writer.toString();
        assertNotNull(result, "렌더링 결과가 null이면 안 된다");
        assertTrue(result.contains("홍길동"),
                "렌더링 결과에 이름이 포함되어야 한다");
        assertTrue(result.contains("홈페이지"),
                "렌더링 결과에 프로젝트명이 포함되어야 한다");
    }
}
