package com.yisingle.navi.library.utils;

import java.util.HashMap;

/**
 * @author jikun
 *         Created by jikun on 2018/3/8.
 */
//http://lbs.amap.com/api/android-sdk/guide/map-tools/error-code/ 错误码对照表
public class GaoDeErrorUtils {
    private static HashMap<Integer, String> errorMAP = new HashMap<>();

    static {
        errorMAP.put(1000, "请求正常" + "服务调用正常，有结果返回");
        errorMAP.put(1001, "开发者签名未通过");
        errorMAP.put(1002, "用户Key不正确或过期");
        errorMAP.put(1003, "没有权限使用相应的接口");
        errorMAP.put(1008, "MD5安全码未通过验证");
        errorMAP.put(1009, "请求Key与绑定平台不符");
        errorMAP.put(1012, "权限不足，服务请求被拒绝");
        errorMAP.put(1013, "该Key被删除");
        errorMAP.put(1100, "引擎服务响应错误");
        errorMAP.put(1101, "引擎返回数据异常");
        errorMAP.put(1102, "高德服务端请求链接超时");
        errorMAP.put(1103, "读取服务结果返回超时");
        errorMAP.put(1200, "请求参数非法");
        errorMAP.put(1201, "请求条件中，缺少必填参数");
        errorMAP.put(1202, "服务请求协议非法");
        errorMAP.put(1203, "服务端未知错误");
        errorMAP.put(1800, "服务端新增错误");
        errorMAP.put(1801, "协议解析错误");
        errorMAP.put(1802, "socket 连接超时 - SocketTimeoutException");
        errorMAP.put(1803, "url异常 - MalformedURLException");
        errorMAP.put(1804, "未知主机 - UnKnowHostException");
        errorMAP.put(1806, "http或socket连接失败 - ConnectionException");
        errorMAP.put(1900, "未知错误");
        errorMAP.put(1901, "参数无效");
        errorMAP.put(1902, "IO 操作异常 - IOException");
        errorMAP.put(1903, "空指针异常 - NullPointException");
        errorMAP.put(2000, "Tableid格式不正确");
        errorMAP.put(2001, "数据ID不存在");
        errorMAP.put(2002, "云检索服务器维护中");
        errorMAP.put(2003, "Key对应的tableID不存在");
        errorMAP.put(2100, "找不到对应的userid信息");
        errorMAP.put(2101, "App Key未开通“附近”功能");
        errorMAP.put(2200, "在开启自动上传功能的同时对表进行清除或者开启单点上传的功能");
        errorMAP.put(2201, "USERID非法");
        errorMAP.put(2202, "NearbyInfo对象为空");
        errorMAP.put(2203, "两次单次上传的间隔低于7秒");
        errorMAP.put(2204, "Point为空，或与前次上传的相同");
        errorMAP.put(3000, "规划点（包括起点、终点、途经点）不在中国陆地范围内");
        errorMAP.put(3001, "规划点（包括起点、终点、途经点）附近搜不到路");
        errorMAP.put(3002, "路线计算失败，通常是由于道路连通关系导致");
        errorMAP.put(3003, "步行算路起点、终点距离过长导致算路失败。");
        errorMAP.put(4000, "短串分享认证失败");
        errorMAP.put(4001, "短串请求失败");
    }

    public static String getErrorInfo(int code) {
        if (null != errorMAP.get(code)) {
            return errorMAP.get(code);
        } else {
            return "未知错误";
        }

    }
}
