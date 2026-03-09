package com.ss.homepage.commons;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Apache Commons BeanUtils 테스트.
 *
 * <p>ASIS: commons-beanutils 1.8.0<br>
 * TOBE: commons-beanutils 1.9.4</p>
 *
 * <p>전환 이유: CVE-2014-0114 등 보안 취약점 패치, PropertyUtils 개선.
 * BeanUtils.copyProperties를 통한 객체 간 프로퍼티 복사가 정상 동작하는지 확인한다.</p>
 */
class BeanutilsTest {

    /**
     * 복사 테스트용 소스 객체.
     */
    public static class Source {
        private String name;
        private int age;

        public Source() {
        }

        public Source(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    /**
     * 복사 테스트용 대상 객체.
     */
    public static class Target {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @Test
    @DisplayName("[TOBE] commons-beanutils 1.9.4 - copyProperties 동작 확인")
    void copyProperties() throws Exception {
        // given: 소스 객체 생성
        Source source = new Source("홍길동", 30);
        Target target = new Target();

        // when: BeanUtils.copyProperties로 프로퍼티 복사
        BeanUtils.copyProperties(target, source);

        // then: 대상 객체에 값이 복사되어야 한다
        assertEquals("홍길동", target.getName(), "이름이 복사되어야 한다");
        assertEquals(30, target.getAge(), "나이가 복사되어야 한다");
    }
}
