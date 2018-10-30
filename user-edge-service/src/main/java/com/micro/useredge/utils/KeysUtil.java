package com.micro.useredge.utils;

import java.util.Random;

/**
 * 随机数生成工具
 * author: mSun
 * date: 2018/10/26
 */
public class KeysUtil {

    /**
     * 毫秒时间戳+6位随机数
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000)+100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }

    /**
     * 生成指定位数随机码
     */
    public static String getRandomCode(int size) {
        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder(size);
        Random random = new Random();
        for(int i=0;i<size; i++) {
            int loc = random.nextInt(str.length());
            result.append(str.charAt(loc));
        }
        return result.toString();
    }



}
