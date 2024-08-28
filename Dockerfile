FROM amazoncorretto:21-alpine-jdk

VOLUME /tmp

ENV TZ=America/Sao_Paulo
RUN apk --no-cache add curl && \
  ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /application.jar"]

HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
    CMD curl -f http://localhost:8001/actuator/health || exit 1
