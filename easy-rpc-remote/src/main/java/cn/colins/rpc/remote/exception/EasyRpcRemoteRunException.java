package cn.colins.rpc.remote.exception;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-13 21:22
 **/
public class EasyRpcRemoteRunException extends RuntimeException{

    String errorMessage;

    public EasyRpcRemoteRunException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
