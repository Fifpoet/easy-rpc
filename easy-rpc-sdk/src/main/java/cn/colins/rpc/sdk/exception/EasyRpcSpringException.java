package cn.colins.rpc.sdk.exception;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-13 21:22
 **/
public class EasyRpcSpringException extends RuntimeException{

    String errorMessage;

    public EasyRpcSpringException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
