# =============================================================================
# Multi-stage 빌드: Maven 빌드 → CentOS 7 + Adoptium JDK 17 런타임
# =============================================================================

# ---------------------------------------------------------------------------
# Stage 1: Build (Maven)
# ---------------------------------------------------------------------------
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# pom.xml을 먼저 복사하여 의존성 레이어를 캐싱한다.
# 소스 코드가 변경되어도 pom.xml이 동일하면 의존성 다운로드를 건너뛴다.
# 참고: dependency:go-offline 대신 dependency:resolve를 사용한다.
#   ehcache의 transitive dependency(jaxb-api 스냅샷)가 HTTP 전용 레포를
#   참조하여 Maven 3.8.1+의 HTTP 미러 차단 정책에 걸리는 문제를 우회한다.
COPY pom.xml .
RUN mvn dependency:resolve -q || true

# 소스 코드 복사 후 패키징 (테스트 생략)
COPY src ./src
RUN mvn package -DskipTests

# ---------------------------------------------------------------------------
# Stage 2: Runtime (CentOS 7 + Adoptium JDK 17)
# ---------------------------------------------------------------------------
# CentOS 7 선택 이유:
#   - JDK 6/7/8/17 모두 지원 가능하여 다양한 버전 테스트에 적합
#   - 레거시 환경(기존 운영 서버) 재현에 적합
#
# 주의: CentOS 7은 2024.06 EOL이지만 테스트 목적으로 사용한다.
#       운영 환경에서는 Rocky Linux 9 등 지원되는 배포판을 권장한다.
FROM centos:7

# Adoptium Temurin RPM 저장소 추가
# 1) RPM 서명 키 등록
RUN rpm --import https://packages.adoptium.net/artifactory/api/gpg/key/public

# 2) Adoptium yum 저장소 파일 생성
RUN echo -e '[Adoptium]\n\
name=Adoptium\n\
baseurl=https://packages.adoptium.net/artifactory/rpm/centos/7/\$basearch\n\
enabled=1\n\
gpgcheck=1\n\
gpgkey=https://packages.adoptium.net/artifactory/api/gpg/key/public' \
> /etc/yum.repos.d/adoptium.repo

# CentOS 7 EOL(2024.06)로 mirrorlist.centos.org 접근 불가.
# vault.centos.org (아카이브)로 baseurl을 전환한다.
RUN sed -i 's/mirrorlist=/#mirrorlist=/g' /etc/yum.repos.d/CentOS-*.repo && \
    sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*.repo

# Temurin JDK 17 설치 후 yum 캐시 정리
RUN yum install -y temurin-17-jdk && yum clean all

WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일 복사
# JAR 패키징을 사용하므로 app.jar로 복사한다 (REST 기반, JSP 없음).
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

# 프로파일은 환경변수(SPRING_PROFILES_ACTIVE)로 지정한다.
# 단독 실행 시에는 기본 프로파일(H2)을 사용한다.
ENTRYPOINT ["java", "-jar", "app.jar"]
