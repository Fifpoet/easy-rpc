package cn.colins.rpc.impl;

import cn.colins.rpc.EasyRpcTest;
import cn.colins.rpc.entiy.UserInfo;
import cn.colins.rpc.sdk.annotation.EasyRpcServicePublish;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/6/14
 */
@EasyRpcServicePublish
public class EasyRpcTestImpl implements EasyRpcTest {
    @Override
    public void test() {

    }

    @Override
    public String test1(UserInfo userInfo) {
        return "ok";
    }

    @Override
    public UserInfo test2(Integer id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("");
        userInfo.setAge(0);
        userInfo.setHobby("");
        userInfo.setAddress("");
        userInfo.setWork("");
        userInfo.setSex(0);
        userInfo.setIdCard("");
        userInfo.setPhone("");
        return userInfo;
    }

    @Override
    public List<UserInfo> test3() {
        List<UserInfo> userInfos=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setName("");
            userInfo.setAge(0);
            userInfo.setHobby("");
            userInfo.setAddress("");
            userInfo.setWork("");
            userInfo.setSex(0);
            userInfo.setIdCard("");
            userInfo.setPhone("");
            userInfos.add(userInfo);
        }
        return userInfos;
    }
}
