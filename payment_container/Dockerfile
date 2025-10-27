# ===== BUILD STAGE =====
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy pom.xml chính
COPY pom.xml ./

# Copy các pom con để cache dependency
COPY ../payment_dataaccess/pom.xml payment_dataaccess/pom.xml
COPY ../payment_domain/payment_domain_core/pom.xml payment_domain/payment_domain_core/pom.xml
COPY ../payment_domain/payment_application_service/pom.xml payment_domain/payment_application_service/pom.xml
COPY ../payment_container/pom.xml payment_container/
COPY ../payment_messaging/pom.xml payment_messaging/
# Tải dependency (cache)
RUN mvn dependency:go-offline -B

# Copy toàn bộ mã nguồn
COPY ../payment_dataaccess payment_dataaccess/
COPY ../payment_domain payment_domain/
COPY ../payment_container payment_container/
COPY ../payment_messaging payment_messaging/
# Build module chính
RUN mvn clean package -pl payment_container -am -DskipTests

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy jar từ stage builder
COPY --from=builder /app/payment_container/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
