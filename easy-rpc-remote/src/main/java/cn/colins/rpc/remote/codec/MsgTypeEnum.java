package cn.colins.rpc.remote.codec;




public enum MsgTypeEnum {
    TEST_TYPE((short) 0xFFF1, "测试");


    private final short code;
    private final String desc;

    MsgTypeEnum(short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public short getCode() {
        return code;
    }

}
