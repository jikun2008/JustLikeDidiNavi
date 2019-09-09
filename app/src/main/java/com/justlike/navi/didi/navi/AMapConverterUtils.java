package com.justlike.navi.didi.navi;

import android.content.Context;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.amap.api.services.core.LatLonPoint;

public class AMapConverterUtils {

    /**
     * 将百度坐标(BD09LL  百度坐标系)转换为高德坐标(火星坐标系)
     *
     * @param context
     * @param latitude
     * @param longitude
     * @return 返回null那么转换失败  不为null则成功
     */
    public static DPoint convert(Context context, double latitude, double longitude) {
        DPoint destPoint = null;

        try {
            DPoint examplePoint = new DPoint(latitude, longitude);
            //初始化坐标转换类
            CoordinateConverter converter = new CoordinateConverter(
                    context);
            /**
             * 设置坐标来源,这里使用百度坐标作为示例
             * 可选的来源包括：
             * <li>CoordType.BAIDU ： 百度坐标
             * <li>CoordType.MAPBAR ： 图吧坐标
             * <li>CoordType.MAPABC ： 图盟坐标
             * <li>CoordType.SOSOMAP ： 搜搜坐标
             * <li>CoordType.ALIYUN ： 阿里云坐标
             * <li>CoordType.GOOGLE ： 谷歌坐标
             * <li>CoordType.GPS ： GPS坐标
             */
            converter.from(CoordinateConverter.CoordType.BAIDU);
            //设置需要转换的坐标
            converter.coord(examplePoint);
            //转换成高德坐标
            destPoint = converter.convert();


        } catch (Exception e) {

            destPoint = null;
        }
        return destPoint;
    }


    /**
     * 该坐标是高德地图可用坐标
     *
     * @param context   Context
     * @param latitude  latitude
     * @param longitude longitude
     * @return true 该坐标是高德地图可用坐标  false该坐标不能用于高德地图
     */
    private boolean checkIsAvaliable(Context context, double latitude, double longitude) {
        //初始化坐标工具类
        //判断是否高德地图可用的坐标
        boolean result = CoordinateConverter.isAMapDataAvailable(latitude, longitude);
        return result;
    }


}