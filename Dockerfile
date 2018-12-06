FROM gradle:4.10-jdk8
LABEL maintainer="kwonghung.yip@gmail.com"

COPY --chown=gradle:gradle . .

RUN chmod u+x gradlew 

RUN ./gradlew build -x test

CMD ["./gradlew", "bootRun"]
