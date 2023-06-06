docker run --name master --privileged --hostname master --ip 172.18.0.2 --add-host=slave1:172.18.0.3  --add-host=slave2:172.18.0.4 --add-host=slave3:172.18.0.5 -itd -v /cgsrc:/cgsrc:ro -v /headless/course/:/course hadoop_node /service_start.sh

docker run --name slave1 --privileged --hostname slave1 --ip 172.18.0.3 --add-host=master:172.18.0.2  --add-host=slave2:172.18.0.4 --add-host=slave3:172.18.0.5  -itd -v /cgsrc:/cgsrc:ro hadoop_node /service_start.sh

docker run --name slave2 --privileged --hostname slave2 --ip 172.18.0.4 --add-host=master:172.18.0.2 --add-host=slave1:172.18.0.3  --add-host=slave3:172.18.0.5 -itd -v /cgsrc:/cgsrc:ro hadoop_node /service_start.sh

docker run --name slave3 --privileged --hostname slave3 --ip 172.18.0.5 --add-host=master:172.18.0.2 --add-host=slave1:172.18.0.3  --add-host=slave2:172.18.0.4 -itd -v /cgsrc:/cgsrc:ro hadoop_node /service_start.sh

docker exec -it --privileged $NAME /bin/bash

docker exec -it --privileged master /bin/bash

docker ps
