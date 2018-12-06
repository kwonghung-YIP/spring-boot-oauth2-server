FROM gradle:4.10-jdk8
LABEL maintainer="kwonghung.yip@gmail.com"

COPY . .

RUN chmod u+x gradlew && ./gradlew build

CMD ["./gradlew", "bootRun"]
