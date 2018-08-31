package com.yisingle.navi.library.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class DistanceTimeUtils {

    /**
     * 将米装换公里
     * 如果小于1000米 则装换为米
     *
     * @param rice
     * @return
     */
    public static String caluaDistance(int rice) {
        String info = "";
        if (rice > 1000) {
            double ditance = rice * 0.001;
            BigDecimal b = new BigDecimal(ditance);
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            info = f1 + "公里";
        } else {
            info = rice + "米";
        }


        return info;
    }

    /**
     * 将s转为 xx小时xx分钟xx秒
     * 例如2000秒 转换为  33分钟
     *
     * @param time 秒
     * @return 字符串
     */
    public static String secToTime(int time) {
        int hour = time / 3600;
        int minute = time % 3600 / 60;
        int second = time % 60;

        String hourString = "";
        if (hour != 0) {
            hourString = hour + "小时";
        }
        String minuteString;
        if (minute != 0) {
            minuteString = minute + "分钟";
        } else {
            minuteString = "1分钟";
        }

//        String secondString = second + "秒";
        return hourString + minuteString;
    }

    public static String millis2String(long time) {

        ////初始化Formatter的转换格式。
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        String hm = formatter.format(time);
        return hm;
    }
}