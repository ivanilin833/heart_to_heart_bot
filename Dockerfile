# Укажите исходный образ Maven для сборки проекта
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Установите рабочую директорию
WORKDIR /app

# Скопируйте файл pom.xml и скачайте зависимости
COPY pom.xml ./
RUN mvn dependency:resolve

# Скопируйте исходный код в контейнер
COPY src ./src

# Соберите приложение
RUN mvn clean package -DskipTests

# Используйте минималистичный базовый образ для финального контейнера
FROM gcr.io/distroless/java17:nonroot

# Установите рабочую директорию
WORKDIR /app

# Скопируйте скомпилированный JAR-файл из предыдущего шага
COPY --from=builder /app/target/heartToHeartBot-0.0.1-SNAPSHOT.jar ./app.jar

# Укажите команду для запуска приложения
CMD ["app.jar"]
