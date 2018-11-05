from message.api import MessageService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

__PORT = "9090"


# 信息发送接口具体实现
class MessageServiceHandle:
    def sendMobileMessage(self, mobile, message):
        print("sendMobileMessage, mobile:" + mobile + ", message:" + message)
        return True

    def sendEmailMessage(self, email, message):
        print("sendEmailMessage, email:" + email + ", message:" + message)
        return True


# 创建Thrift服务端
if __name__ == "__main__":
    # 定义调用处理类
    handler = MessageServiceHandle()
    processor = MessageService.Processor(handler)
    # 传输端口
    transport = TSocket.TServerSocket(host=None, port=__PORT)
    # 传输方式(帧传输)
    tfactory = TTransport.TFramedTransportFactory()
    # 传输协议
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()
    # 起服务
    server = TServer.TThreadPoolServer(processor, transport, tfactory, pfactory)
    print("python thrift server start")
    server.serve()
    print("python thrift server exit")
