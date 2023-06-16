
package cn.colins.rpc.common.entiy;


/**
 * 业务代码枚举
 *
 * @author Chill
 */

public enum EasyRpcResultCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 服务器异常
     */
    TIME_OUT(401, "请求超时"),

    /**
     * 服务器异常
     */
    SERVER_ERROR(500, "请求服务器异常"),

    /**
     * 404 没找到请求
     */
    NOT_FOUND(404, "404 没找到请求"),

    /**
     * 参数校验失败
     */
    PARAM_VALID_ERROR(400, "参数校验失败");


    /**
     * code编码
     */
    final int code;
    /**
     * 中文信息描述
     */
    final String message;

     EasyRpcResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
