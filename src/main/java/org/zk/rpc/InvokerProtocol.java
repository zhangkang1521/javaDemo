package org.zk.rpc;

import lombok.Data;

import java.io.Serializable;

@Data
public class InvokerProtocol implements Serializable {
    private String className;
//    private String methodName;
//    private Class<?>[] parames;
//    private Object[] values;

}
