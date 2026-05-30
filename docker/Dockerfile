# ============================================
# Production Dockerfile for Spring Boot
# Multi-stage build
# ============================================

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /build

# Cache dependencies separately from code
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Build the application
COPY src ./src
RUN mvn clean package -DskipTests -q

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine AS runtime

LABEL maintainer="monish@devops.com"
LABEL version="1.0.0"

# Security: non-root user
RUN addgroup -S appgroup && \
    adduser -S appuser -G appgroup

WORKDIR /app

# Copy JAR from builder
COPY --from=builder \
     /build/target/*.jar \
     app.jar

RUN chown appuser:appgroup app.jar

USER appuser

# JVM tuning for containers
ENV JAVA_OPTS="\
  -XX:+UseContainerSupport \
  -XX:MaxRAMPercentage=75.0 \
  -XX:+ExitOnOutOfMemoryError \
  -Djava.security.egd=file:/dev/./urandom"

HEALTHCHECK --interval=30s \
            --timeout=10s \
            --start-period=40s \
            --retries=3 \
    CMD wget -q --spider \
        http://localhost:8080/actuator/health \
        || exit 1

EXPOSE 8080

ENTRYPOINT ["sh", "-c", \
  "java $JAVA_OPTS -jar app.jar"]
