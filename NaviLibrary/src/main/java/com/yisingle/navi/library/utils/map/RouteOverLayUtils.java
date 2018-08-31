package com.yisingle.navi.library.utils.map;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.view.RouteOverLay;
import com.yisingle.navi.library.R;


/**
 *
 */
public class RouteOverLayUtils {


    /**
     * 绘制路线
     */
    public static RouteOverLay drawRouteOverLay(Context context, AMap mAMap, AMapNaviPath path) {
        //设置路径规划后起点 用来进行地图全览。

        mAMap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(mAMap, path, context);
        //设置起点图片
        routeOverLay.setStartPointBitmap(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.driver_navi_start));
        //设置终点图片
        routeOverLay.setEndPointBitmap(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.driver_navi_restdistance));
        //设置导航路线纹理图效果
        routeOverLay.setRouteOverlayOptions(NaviOptionsUtils.generateOptions(context.getResources()));

        routeOverLay.setTrafficLine(true);

        routeOverLay.setTrafficLightsVisible(false);

        routeOverLay.addToMap();

        routeOverLay.setTransparency(1.0F);

        return routeOverLay;
    }

    public static void cleanRouteOverLay(RouteOverLay routeOverLay) {
        if (null != routeOverLay) {
            routeOverLay.removeFromMap();
            routeOverLay.destroy();
        }
    }
}
