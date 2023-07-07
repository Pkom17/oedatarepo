FROM openjdk:11-jre-slim
WORKDIR /app
COPY **/target/oedatarepo-O.1.jar .
EXPOSE 8085
ENTRYPOINT ["java","-jar","./oedatarepo-O.1.jar"]


