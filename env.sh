docker run --name master --privileged --hostname master2 --add-host=slave1:172.18.0.3  --add-host=slave2:172.18.0.4 --add-host=slave3:172.18.0.5 -itd -v /cgsrc:/cgsrc:ro -v /headless/course/:/course hadoop_node /service_start.sh

docker exec -it --privileged master1 /bin/bash

docker ps
