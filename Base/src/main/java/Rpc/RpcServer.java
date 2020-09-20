package Rpc;

import java.io.IOException;

/**
 * Created by hfc on 2020/9/14.
 */
public class RpcServer {

    public static void main(String[] args) throws IOException {
        // 服务提供者暴露出接口
        RpcServiceIfc service = new RpcServiceImpl();
        RpcFramework.export(service, 10086);
    }
}
