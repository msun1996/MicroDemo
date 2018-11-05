docker build -t python-thrift:latest -f Dockerfile.base .

docker build -t message-service:latest .

# 运行
docker run -it message-service:latest

