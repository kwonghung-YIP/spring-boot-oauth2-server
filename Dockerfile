FROM gradle:4.10-jdk8
LABEL maintainer="kwonghung.yip@gmail.com"

RUN mkdir -p /usr/local/springboot

WORKDIR /usr/local/springboot

COPY . .

RUN chmod +w gradlew && gradlew build

CMD ["./gradlew", "bootRun"]
