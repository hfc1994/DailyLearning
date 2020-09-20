package Rpc;

/**
 * Created by hfc on 2020/9/14.
 */
public class RpcServiceImpl implements RpcServiceIfc {

    @Override
    public String hello(String name) {
        return "hey man, you say you are " + name;
    }

}
