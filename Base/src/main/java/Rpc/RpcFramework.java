package Rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hfc on 2020/9/14.
 */
public class RpcFramework {

    public static void export(Object service, int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        System.out.println("server start...");
        while (true) {
            Socket socket = server.accept();
            System.out.println("new connection...");
            new Thread(() -> {
                // 反序列化
                try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
                    // 读取方法名
                    String methodName = input.readUTF();
                    // 参数类型
                    Class<?>[] parameterType = (Class<?>[]) input.readObject();
                    // 参数
                    Object[] args = (Object[]) input.readObject();
                    // 获取方法
                    Method method = service.getClass().getMethod(methodName, parameterType);
                    // 调用方法
                    Object result = method.invoke(service, args);
                    // 序列化，然后返回结果
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeObject(result);
                    output.flush();
                } catch (IOException | ClassNotFoundException | NoSuchMethodException
                        | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T refer(Class<T> interfaceClass, String host, int port) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                (proxy, method, args) -> {
                    // 指定provider的ip和端口
                    System.out.println("begin connect...");
                    Socket socket = new Socket(host, port);
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    // 传方法名
                    output.writeUTF(method.getName());
                    // 传参数类型
                    output.writeObject(method.getParameterTypes());
                    // 传参数值
                    output.writeObject(args);
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                    // 读取结果
                    return input.readObject();
                });
    }
}
