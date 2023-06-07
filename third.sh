#!/bin/bash
docker stop master01 master slave1 slave2 slave3
docker commit master01 hellohadoop
docker rm -f master01
docker run --name master1 --privileged --hostname master --ip 172.18.0.2 --add-host=slave1:172.18.0.3  --add-host=slave2:172.18.0.4 --add-host=slave3:172.18.0.5 -itd -v /cgsrc:/cgsrc:ro -v /headless/course/:/course hellohadoop /service_start.sh

docker run --name slave11 --privileged --hostname slave1 --ip 172.18.0.3 --add-host=master:172.18.0.2  --add-host=slave2:172.18.0.4 --add-host=slave3:172.18.0.5  -itd -v /cgsrc:/cgsrc:ro hellohadoop /service_start.sh

docker run --name slave12 --privileged --hostname slave2 --ip 172.18.0.4 --add-host=master:172.18.0.2 --add-host=slave1:172.18.0.3  --add-host=slave3:172.18.0.5 -itd -v /cgsrc:/cgsrc:ro hellohadoop /service_start.sh

docker run --name slave13 --privileged --hostname slave3 --ip 172.18.0.5 --add-host=master:172.18.0.2 --add-host=slave1:172.18.0.3  --add-host=slave2:172.18.0.4 -itd -v /cgsrc:/cgsrc:ro hellohadoop /service_start.sh

docker ps 

exit
