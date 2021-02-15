package com.github.xabgesagtx.example.jni;

public class Jni {

    /**
     * 查询设备是否在线
     * @param group
     * @param cloudSeeId
     * @param timeout
     * @return
     */
    public static native int isDeviceOnline(String group, int cloudSeeId, int timeout);

    /**
     * 查询设备的端口数
     * @param group
     * @param cloudSeeId
     * @param timeout
     * @return
     */
    public static native int getChannelCount(String group, int cloudSeeId, int timeout);

    /**
     * 初始化sdk
     * @return
     */
    public static native int initSdk();

    public static native void destory();


}