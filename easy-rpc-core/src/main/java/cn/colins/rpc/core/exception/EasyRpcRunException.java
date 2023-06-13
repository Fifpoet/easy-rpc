package cn.colins.rpc.core.exception;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class EasyRpcRunException extends RuntimeException {


    String errorMessage;

    public EasyRpcRunException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
