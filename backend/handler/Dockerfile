FROM openjdk:17-jdk-alpine
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY ./build/libs/handler-0.0.1.jar ./handler.jar
CMD ["java","-jar","handler.jar"]