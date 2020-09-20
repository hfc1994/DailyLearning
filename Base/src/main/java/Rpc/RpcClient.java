package Rpc;

/**
 * Created by hfc on 2020/9/14.
 *
 * 参考文章：https://juejin.im/post/6870276943448080392#heading-8
 */
public class RpcClient {

    public static void main(String[] args) {
        // 服务调用者设置依赖
        RpcServiceIfc service = RpcFramework.refer(RpcServiceIfc.class, "127.0.0.1", 10086);
        String ret = service.hello("jack ma");
        System.out.println(ret);
    }
}
