package cn.colins.rpc.remote.entiy;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/12
 */
public class EasyRpcResponse {
    private int code;
    private String msg;
    private Object data;
    private String exception;


    private EasyRpcResponse(int code, String msg, Object data, String exception) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.exception = exception;
    }

    private static EasyRpcResponse result(int code, String msg, Object data, String exception) {
        return new EasyRpcResponse(code, msg, data, exception);
    }

    public static EasyRpcResponse success() {
        return result(EasyRpcResultCode.SUCCESS.code, EasyRpcResultCode.SUCCESS.message, null, null);
    }

    public static EasyRpcResponse success(Object data) {
        return result(EasyRpcResultCode.SUCCESS.code, EasyRpcResultCode.SUCCESS.message, data, null);
    }

    public static EasyRpcResponse success(EasyRpcResultCode easyRpcResultCode) {
        return result(easyRpcResultCode.code, easyRpcResultCode.message, null, null);
    }

    public static EasyRpcResponse success(EasyRpcResultCode easyRpcResultCode, Exception e) {
        return result(easyRpcResultCode.code, easyRpcResultCode.message, null, e.getMessage());
    }

    public static EasyRpcResponse success(int code, String msg, Exception e) {
        return result(code, msg, null, e.getMessage());
    }

    public static EasyRpcResponse error(Exception e) {
        return result(EasyRpcResultCode.SERVER_ERROR.code, EasyRpcResultCode.SERVER_ERROR.message, null, e.getMessage());
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
