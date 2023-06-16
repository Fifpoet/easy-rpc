package cn.colins.rpc;

import cn.colins.rpc.entiy.UserInfo;

import java.util.List;

public interface EasyRpcTest {

    void test();

    String test1(UserInfo userInfo);

    UserInfo test2(Integer id);

    List<UserInfo> test3();
}
