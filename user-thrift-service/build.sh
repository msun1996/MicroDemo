#!/bin/bash
# 打包程序
docker build -t user-thrift-service:latest .
# 运行程序
# docker run -it user-thrift-service:latest --mysql.address=192.168.99.1 --mysql.username=jd_test --mysql.password=Hzy12345678
