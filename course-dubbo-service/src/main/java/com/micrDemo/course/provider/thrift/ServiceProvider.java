package com.micrDemo.course.provider.thrift;

import com.micro.thrift.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 创建Thrift客户端
 * author: mSun
 * date: 2018/10/26
 */
@Component
@Slf4j
public class ServiceProvider {

    @Value("${thrift.server.user.ip}")
    private String userServerIp;

    @Value("${thrift.server.user.port}")
    private Integer userServerPort;

    private enum ServiceType{
        USER,
    }

    /**
     * 用户信息获取
     * @return
     */
    public UserService.Client getUserService() {
        return getService(userServerIp,userServerPort, ServiceType.USER);
    }

    public <T> T getService(String ip, Integer port, ServiceType serviceType) {
        TSocket socket = new TSocket(ip,port,3000);
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (TTransportException e) {
            log.error("【{}】服务连接失败", serviceType);
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(transport);
        TServiceClient serviceClient = null;
        switch (serviceType) {
            case USER:
                serviceClient = new UserService.Client(protocol);
                break;
        }
        return (T) serviceClient;

    }
}
