package cn.colins.rpc.core.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.Assert;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/13
 */
public class EasyRpcApplicationConfig {
    /** RPC 应用名 */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Assert.isTrue(StrUtil.isNotEmpty(this.name), "application name cannot be empty");
    }
}
