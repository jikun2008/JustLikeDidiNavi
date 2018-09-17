package com.yisingle.navi.library.base.navi;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.BroadcastMode;
import com.amap.api.navi.model.AMapModeCrossOverlay;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.NextTurnTipView;
import com.amap.api.navi.view.RouteOverLay;
import com.yisingle.navi.library.utils.DpSpPxScreenUtils;
import com.yisingle.navi.library.utils.map.NaviOptionsUtils;
import com.yisingle.navi.library.widget.SimpleRouteView;


/**
 * @author jikun
 * Created by jikun on 2018/3/29.
 */

public abstract class BaseNaviFragment extends BaseNaviLifeCycleFragment {

    /**
     * 导航对外控制类,用来进行路径规划等功能
     */
    protected AMapNavi mAMapNavi;


    /**
     * NaviView上绘制的路线View
     */
    protected SimpleRouteView naviRouteView;


    protected AMapModeCrossOverlay modeCrossOverlay;


    /**
     * AMapNavi导航对象
     * 初始化导航对象 NaviView和这个关系重要。
     */
    protected void initAMapNavi() {
        //请注意这个是单例模式
        mAMapNavi = AMapNavi.getInstance(getContext());
        //1-CONCISE 专家播报 简洁播报（播报更精简，建议老司机使用）
        //2-DETAIL 新手播报 详细播报（更关注变道提醒，安全提示
        mAMapNavi.setBroadcastMode(BroadcastMode.DETAIL);
        //添加导航监听器
        mAMapNavi.addAMapNaviListener(this);
        // 使用5 .7 .0 导航版本 2018 年01月10日更新的内置语音导航
        //导航可以使用内部语音导航, 但是暂时不做修改
        mAMapNavi.setUseInnerVoice(true);


    }

    /**
     * 初始化NaviView的一些参数并调用 AMapNaviView.onCreate方法。
     *
     * @param savedInstanceState savedInstanceState
     */
    protected void initNaviView(Bundle savedInstanceState, AMapNaviView naviView) {
        naviRouteView = new SimpleRouteView();
        //绑定导航View到Actity 其实就是设置NaviView给Activity
        bindNaviViewToActivity(naviView);
        if (null != getNaviView() && null != getNaviView().getViewOptions()) {
            //获取NaviViewOptions的参数对象。
            AMapNaviViewOptions options = getNaviView().getViewOptions();
            //设置NaviViewOptions的参数对象
            getNaviView().setViewOptions(NaviOptionsUtils.configureOptions(getContext(), options));
            //调用NaviView的生命周期
            getNaviView().onCreate(savedInstanceState);
        }
        //用来设置UiSetting 一些 具体请在查看高德api文档
        if (null != getNaviView().getMap() && null != getNaviView().getMap().getUiSettings()) {
            modeCrossOverlay=new AMapModeCrossOverlay(getContext(),getNaviView().getMap());
            getNaviView().getMap().setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
                @Override
                public void onMapLoaded() {
                    UiSettings uiSettings = getNaviView().getMap().getUiSettings();
                    //设置Logo在底部左下角
                    uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
                    //设置Logo距离左边15dp;
                    uiSettings.setLogoLeftMargin(DpSpPxScreenUtils.dip2px(getContext(), 15));
                    //导航NaviView加载完成后回调

                }
            });

        }

    }

    /**
     * 设置转向图片的数组到TurnView中去
     * NextTurnTipView 这个控件高德SDK提供的,主要是用来显示图片功能,继承ImageView
     *
     * @param bigTurnView bigTurnView
     * @param littleView  littleView
     */
    protected void initTurnView(@NonNull NextTurnTipView bigTurnView, @NonNull NextTurnTipView littleView) {
        bigTurnView.setCustomIconTypes(getResources(), NaviOptionsUtils.getCustomIconTypes());
        littleView.setCustomIconTypes(getResources(), NaviOptionsUtils.getCustomIconTypes());
    }


    protected void drawRouteViewOnNaviView(int routeId) {
        cleanRouteViewOnNaviView();
        if (null != getNaviView() && null != mAMapNavi) {
            //设置导航选择路线
            mAMapNavi.selectRouteId(routeId);
            //在NaviView上绘制路线
            naviRouteView.drawView(getContext(), getNaviView().getMap(), routeId, mAMapNavi);
        }

    }

    /**
     * 画走过的路线
     *
     * @param naviLocation
     */
    protected void drawAfterRoute(AMapNaviLocation naviLocation) {

        RouteOverLay routeOverLay = naviRouteView.getRouteOverLay();
        routeOverLay.updatePolyline(naviLocation);


    }


    protected void cleanRouteViewOnNaviView() {
        naviRouteView.cleanView();
    }

    protected void drawArrow(NaviInfo naviInfo) {
        naviRouteView.drawArrow(naviInfo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cleanRouteViewOnNaviView();

        if (null != getNaviView()) {
            getNaviView().onDestroy();
        }
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        mAMapNavi.removeAMapNaviListener(this);
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
    }

    public abstract void showLoading(String info);

    public abstract void showSuccess();

    public abstract void showError(String info);

    /**
     * 在导航的地图NaviView上移动视角
     */
    public void moveCameraOnNaviView(NaviLatLng start, NaviLatLng end, int paddingTop) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();


        builder.include(new LatLng(start.getLatitude(), start.getLongitude()));
        builder.include(new LatLng(end.getLatitude(), end.getLongitude()));
        LatLngBounds latLngBounds = builder.build();
        //newLatLngBoundsRect(LatLngBounds latlngbounds,
        //int paddingLeft,设置经纬度范围和mapView左边缘的空隙。
        //int paddingRight,设置经纬度范围和mapView右边缘的空隙
        //int paddingTop,设置经纬度范围和mapView上边缘的空隙。
        //int paddingBottom)设置经纬度范围和mapView下边缘的空隙。
        if (null != getNaviView() && null != getNaviView().getMap()) {
            getNaviView().getMap().animateCamera(
                    CameraUpdateFactory.newLatLngBoundsRect(latLngBounds, 80, 80, paddingTop + 80, 80)
            );
        }


    }
}
