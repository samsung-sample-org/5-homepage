package com.ss.homepage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * 샘플 엔티티.
 *
 * <p>ASIS: Hibernate 3.1 + javax.persistence (JPA 2.x)<br>
 * TOBE: Hibernate 6.x + jakarta.persistence (JPA 3.1, Spring Boot 3)</p>
 *
 * <p>전환 이유: Spring Boot 3은 Jakarta EE 9+ 네임스페이스를 사용하므로,
 * 기존 {@code javax.persistence} 패키지의 모든 임포트를
 * {@code jakarta.persistence}로 변경해야 한다.
 * Hibernate 6.x는 Jakarta Persistence 3.1을 구현하며,
 * Spring Boot 3의 spring-boot-starter-data-jpa에 포함되어 있다.</p>
 */
@Entity
@Table(name = "sample")
public class SampleEntity {

    /** 기본키 (자동 생성) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 이름 */
    @Column(nullable = false)
    private String name;

    /** 설명 */
    @Column
    private String description;

    /** 생성일시 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 기본 생성자 (JPA 필수)
    protected SampleEntity() {
    }

    public SampleEntity(String name, String description, LocalDateTime createdAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    // --- Getter / Setter ---

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
