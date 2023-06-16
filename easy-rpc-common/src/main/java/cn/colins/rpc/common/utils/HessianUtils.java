package cn.colins.rpc.common.utils;


import cn.colins.rpc.common.exception.EasyRpcRunException;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/15
 */
public class HessianUtils {

    public static Object parseObject(byte[] data) {
        try {
            // 使用 Hessian 进行反序列化
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            Hessian2Input hi = new Hessian2Input(bis);
            Object o = hi.readObject();
            hi.close();
            bis.close();
            return o;
        }catch (Exception e){
            throw new EasyRpcRunException(e.getMessage());
        }
    }

    public static byte[] toBytes(Object o) {
        try{
            // 使用 Hessian 进行序列化
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Hessian2Output output = new Hessian2Output(os);
            output.writeObject(o);
            output.close();
            byte[] data = os.toByteArray();
            os.close();
            return data;
        }catch (Exception e){
            throw new EasyRpcRunException(e.getMessage());
        }
    }
}
