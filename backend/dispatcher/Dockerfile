FROM openjdk:17-jdk-alpine
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY ./build/libs/dispatcher-0.0.1.jar ./dispatcher.jar
CMD ["java","-jar","dispatcher.jar"]