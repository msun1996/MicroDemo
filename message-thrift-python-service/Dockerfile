FROM python-thrift:latest
MAINTAINER hanzhaoyang hanzhaoyang@jd.com

# 环境变量
ENV PYTHONPATH /
ADD message /message

ENTRYPOINT ["python", "/message/message_service.py"]
