FROM adoptopenjdk/openjdk11
RUN apt update
RUN apt -y install graphicsmagick-libmagick-dev-compat
COPY target/*.jar app.jar
COPY ./parser/parser /parser/parser