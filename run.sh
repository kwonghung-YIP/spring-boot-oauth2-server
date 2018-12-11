mkdir -p gradle_cache

docker run --rm -it \
  -v `pwd`:/usr/local/project \
  -v `pwd`/gradle_cache:/home/gradle/.gradle \
  -w /usr/local/project \
  gradle:4.10-jdk8 \
  gradle build -x test
  
docker run --rm -it \
  -v `pwd`/haproxy.cfg:/usr/local/etc/haproxy/haproxy.cfg \
  haproxy:1.8.14 \
  haproxy -c -f /usr/local/etc/haproxy/haproxy.cfg
  
docker-compose up --detach
