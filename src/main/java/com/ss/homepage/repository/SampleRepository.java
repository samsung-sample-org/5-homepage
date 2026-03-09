package com.ss.homepage.repository;

import com.ss.homepage.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 샘플 엔티티 JPA 리포지토리.
 *
 * <p>ASIS: MyBatis / Hibernate 3.1 (DAO 수동 구현)<br>
 * TOBE: Spring Data JPA (JpaRepository 상속, 메서드 자동 구현)</p>
 */
@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
