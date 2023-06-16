package cn.colins.rpc.remote.entiy;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EasyRpcRequest implements Serializable {
    /**
     * 客户端需要同步获取响应结果 每个消息需要唯一标识
     */
    private String requestId;
    /**
     * 需要调用远程的Bean的别名
     */
    private String beanRef;       //别名

    /**
     * 需要调用远程的接口
     */
    private String interfaces;
    /**
     * 需要调用的方法
     */
    private String methodName;  //方法
    /**
     * 需要调用的方法的入参属性
     */
    private Class[] paramTypes; //入参属性
    /**
     * 需要调用的方法的入参
     */
    private Object[] args;      //入参



    public EasyRpcRequest(String requestId,String beanRef, String interfaces, String methodName, Class[] paramTypes, Object[] args) {
        this.requestId = requestId;
        this.beanRef = beanRef;
        this.interfaces = interfaces;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.args = args;
    }

    public String getBeanRef() {
        return beanRef;
    }

    public void setBeanRef(String alias) {
        this.beanRef = alias;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }

    public String getRequestId() {
        return requestId;
    }
}
