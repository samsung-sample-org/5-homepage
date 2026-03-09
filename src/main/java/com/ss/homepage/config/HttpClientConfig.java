package com.ss.homepage.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Apache HttpClient 5 설정.
 *
 * <p>ASIS: Apache HttpClient 4.3 (org.apache.http 패키지, groupId: org.apache.httpcomponents:httpclient)<br>
 * TOBE: Apache HttpClient 5 (org.apache.hc.client5 패키지, groupId: org.apache.httpcomponents.client5:httpclient5)</p>
 *
 * <p>전환 이유:</p>
 * <ul>
 *   <li>HttpClient 4.x는 더 이상 주요 업데이트가 제공되지 않으며, Java 17+ 환경에서 일부 호환성 문제가 존재한다.</li>
 *   <li>HttpClient 5.x는 HTTP/2 지원, 비동기 처리 개선, 패키지 구조 전면 변경(org.apache.http → org.apache.hc)이 이루어졌다.</li>
 *   <li>Maven groupId가 {@code org.apache.httpcomponents}에서 {@code org.apache.httpcomponents.client5}로 완전 변경되었으므로,
 *       의존성 선언부터 임포트까지 전면 수정이 필요하다.</li>
 * </ul>
 */
@Configuration
public class HttpClientConfig {

    /**
     * 커넥션 풀을 사용하는 CloseableHttpClient 빈을 생성한다.
     *
     * @return 풀링 커넥션 매니저가 적용된 HttpClient 인스턴스
     */
    @Bean
    public CloseableHttpClient closeableHttpClient() {
        // 커넥션 풀 매니저 설정
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(50);              // 전체 최대 커넥션 수
        connectionManager.setDefaultMaxPerRoute(20);    // 라우트(호스트)당 최대 커넥션 수

        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
    }
}
