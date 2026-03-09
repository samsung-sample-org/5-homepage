package com.ss.homepage.cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Ehcache 3 JCache 통합 테스트.
 *
 * <p>ASIS: ehcache 1.2.3 / 2.5.0 (javax 기반, net.sf.ehcache)<br>
 * TOBE: ehcache 3.10.8 (jakarta 호환, JCache/JSR-107 표준, org.ehcache)</p>
 *
 * <p>전환 이유: Ehcache 2.x는 EOL이며 Spring Boot 3(Jakarta EE)과 호환되지 않는다.
 * Ehcache 3.x는 JCache(JSR-107) 표준을 완전 구현하며, Spring Boot의
 * {@code spring.cache.jcache.config} 설정을 통해 자동 연동된다.</p>
 *
 * <p>ehcache.xml에 정의된 {@code sampleCache}의 존재 여부와
 * put/get 동작을 확인한다.</p>
 */
@SpringBootTest
class Ehcache3Test {

    @Autowired
    private CacheManager cacheManager;

    @Test
    @DisplayName("[TOBE] ehcache 3.10.8 - CacheManager 주입 확인")
    void cacheManagerInjected() {
        // Spring CacheManager가 정상적으로 주입되는지 확인한다
        assertNotNull(cacheManager, "CacheManager가 null이면 안 된다");
    }

    @Test
    @DisplayName("[TOBE] ehcache 3.10.8 - sampleCache 존재 확인")
    void sampleCacheExists() {
        // ehcache.xml에 정의된 sampleCache가 존재하는지 확인한다
        Cache sampleCache = cacheManager.getCache("sampleCache");
        assertNotNull(sampleCache, "sampleCache가 존재해야 한다");
    }

    @Test
    @DisplayName("[TOBE] ehcache 3.10.8 - 캐시 put/get 동작 확인")
    void cachePutAndGet() {
        // 캐시에 값을 저장하고 조회하여 동일한 값이 반환되는지 확인한다
        Cache sampleCache = cacheManager.getCache("sampleCache");
        assertNotNull(sampleCache, "sampleCache가 존재해야 한다");

        String key = "testKey";
        String value = "테스트 캐시 값";

        sampleCache.put(key, value);

        Cache.ValueWrapper wrapper = sampleCache.get(key);
        assertNotNull(wrapper, "캐시에서 조회한 ValueWrapper가 null이면 안 된다");
        assertEquals(value, wrapper.get(),
                "캐시에서 조회한 값이 저장한 값과 일치해야 한다");
    }
}
