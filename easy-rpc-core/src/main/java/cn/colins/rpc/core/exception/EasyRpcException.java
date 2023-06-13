package cn.colins.rpc.core.exception;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class EasyRpcException extends Exception {


    String errorMessage;

    public EasyRpcException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
