docker run --rm -it \
  -v `pwd`:/usr/local/project \
  -w /usr/local/project \
  gradle:4.10-jdk8 \
  bash
