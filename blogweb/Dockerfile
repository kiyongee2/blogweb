# ① 빌드 단계: JDK 21로 Gradle 빌드 → 실행 가능한 jar 만들기
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew clean bootJar -x test --no-daemon

# ② 실행 단계: 가벼운 JRE 21 이미지에 jar만 복사
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# 컨테이너에서는 운영(prod) 프로파일로 실행
ENV SPRING_PROFILES_ACTIVE=prod
# 무료 인스턴스 메모리 512MB에 맞춰 자바 힙 제한
ENV JAVA_OPTS="-Xmx300m"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
