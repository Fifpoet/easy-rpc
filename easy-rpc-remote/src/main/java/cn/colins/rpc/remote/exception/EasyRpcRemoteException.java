package cn.colins.rpc.remote.exception;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-13 21:22
 **/
public class EasyRpcRemoteException extends Exception{

    String errorMessage;

    public EasyRpcRemoteException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
