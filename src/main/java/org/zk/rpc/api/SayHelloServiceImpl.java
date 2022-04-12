package org.zk.rpc.api;

public class SayHelloServiceImpl implements SayHelloService {

    public String sayHello(String msg) {
        return "hello," +msg;
    }
}
