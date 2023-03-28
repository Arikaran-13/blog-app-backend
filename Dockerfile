
FROM openjdk:19

WORKDIR /app

COPY . .

EXPOSE 8080

EXPOSE 3306

COPY target/blog-app-0.0.1-SNAPSHOT.jar blog-app-img.jar


ENTRYPOINT ["java","-jar","blog-app-img.jar"]