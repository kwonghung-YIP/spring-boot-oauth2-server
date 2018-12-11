mkdir gradle_cache

docker run --rm -it \
  -v `pwd`:/usr/local/project \
  -v `pwd`/gradle_cache:/home/gradle/.gradle \
  -w /usr/local/project \
  gradle:4.10-jdk8 \
  gradle build -x test
