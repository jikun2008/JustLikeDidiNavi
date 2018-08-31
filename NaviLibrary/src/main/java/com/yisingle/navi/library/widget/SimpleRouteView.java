package com.yisingle.navi.library.widget;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.yisingle.navi.library.utils.map.RouteOverLayUtils;


import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/4/4.
 * 简单的用来显示在地图上路线
 */

public class SimpleRouteView {

    private int routeId;

    private RouteOverLay routeOverLay;


    private AMapNaviPath mapNaviPath;

    private boolean isSelect;


    public SimpleRouteView() {

    }

    public void drawView(Context context, AMap amap, int routeId, AMapNavi mapNavi) {

        this.routeId = routeId;
        //先将历史的naviRouteOverLay清除掉
        RouteOverLayUtils.cleanRouteOverLay(routeOverLay);
        //绘制路线,这里的Amap请传递导航NaviView的Amap
        mapNaviPath = mapNavi.getNaviPaths().get(routeId);
        routeOverLay = RouteOverLayUtils.drawRouteOverLay(context, amap, mapNaviPath);


    }

    public void drawArrow(NaviInfo naviInfo) {
        //画导航路线上的箭头
        if (null != routeOverLay && null != naviInfo && null != routeOverLay.getArrowPoints(naviInfo.getCurStep())) {
            List<NaviLatLng> naviLatLngList = routeOverLay.getArrowPoints(naviInfo.getCurStep());
            routeOverLay.drawArrow(naviLatLngList);
        }
    }


    public void cleanView() {
        RouteOverLayUtils.cleanRouteOverLay(routeOverLay);

    }



    public AMapNaviPath getMapNaviPath() {
        return mapNaviPath;
    }

    public boolean isSelect() {
        return isSelect;
    }


    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public RouteOverLay getRouteOverLay() {
        return routeOverLay;
    }


}
