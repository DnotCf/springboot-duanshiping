package com.tang.utils;

public enum VideoStatusEnum {

    SUCCESS(1),   //发布成功
    FORBID(2);     //禁止播放

    public final int value;

    VideoStatusEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
