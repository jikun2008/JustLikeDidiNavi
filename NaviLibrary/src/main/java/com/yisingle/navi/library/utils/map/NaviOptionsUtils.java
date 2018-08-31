package com.yisingle.navi.library.utils.map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.RouteOverlayOptions;
import com.yisingle.navi.library.R;
import com.yisingle.navi.library.utils.DpSpPxScreenUtils;


/**
 * @author jikun
 * Created by jikun on 2018/3/30.
 */

public class NaviOptionsUtils {



    /**
     * 定义转向图标的数组
     */
    private static int[] customIconTypes = {R.drawable.sou2, R.drawable.sou3,
            R.drawable.sou4, R.drawable.sou5, R.drawable.sou6, R.drawable.sou7,
            R.drawable.sou8, R.drawable.sou9, R.drawable.sou10,
            R.drawable.sou11, R.drawable.sou12, R.drawable.sou13,
            R.drawable.sou14, R.drawable.sou15, R.drawable.sou16,
            R.drawable.sou17, R.drawable.sou18
    };

    /**
     * 返回定义转向图标的数组
     */
    public static int[] getCustomIconTypes() {
        return customIconTypes;
    }


    public static AMapNaviViewOptions configureOptions(Context context, @NonNull AMapNaviViewOptions options) {

        //设置前方拥堵时不重新计算路径（只适用于驾车导航，需要联网）
        options.setReCalculateRouteForTrafficJam(false);
        //关闭自动绘制路线（如果你想自行绘制路线的话，必须关闭！！！）非常重要
        options.setAutoDrawRoute(false);
        //设置导航UI是否显示
        options.setLayoutVisible(false);
        //设置是否显示路口放大图(路口模型图)
        options.setModeCrossDisplayShow(true);
        /**
         * 设置路口模型图的显示的位置。
         * 模型图 相关方法 showModeCross(AMapModelCross aMapModelCross) hideModeCross()
         * 模型图的显示 并不能使用showModeCross的AMapModelCross   ImageView加载图片来显示。
         * 你必须使用setCrossLocation来设置模型图的显示位置
         * 因为模型图在放在NaviView里面的。
         * 路口放大图分为模型图和实景图。
         * 这里设置的是模型图的位置.
         * 实景图可通过自定义ZoomInIntersectionView进行设置.
         */
        Rect rect = new Rect(0, DpSpPxScreenUtils.dip2px(context,50), DpSpPxScreenUtils.getScreenWidth(context), DpSpPxScreenUtils.dip2px(context,210));
        options.setCrossLocation(rect, rect);
        //设置导航状态下屏幕是否一直开启。
        options.setScreenAlwaysBright(true);
        //设置路况光柱条是否显示（只适用于驾车导航，需要联网）。
        options.setTrafficBarEnabled(false);
        //设置导航起点图片
        options.setStartPointBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.driver_navi_start));
        //设置导航终点图片
        options.setEndPointBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.driver_navi_restdistance));
        //设置导航定位车辆图片
        options.setCarBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.driver_navi_car_circle));
        //设置罗盘位图图片
        options.setFourCornersBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.driver_navi_direction));
        /**
         * 设置自车位置锁定在屏幕中的位置
         * 参数:
         * x - 取值范围：0-1 在x轴的位置，百分比
         * y - 取值范围：0-1 在y轴的位置，百分比
         */
        options.setPointToCenter(0.5, 0.7);

        /**
         * 设置是否绘制牵引线（当前位置到目的地的指引线）。默认不绘制牵引线。
         * color - 设置牵引线颜色，为ARGB格式。不显示牵引线时，颜色设置为-1即可。
         */
        options.setLeaderLineEnabled(Color.RED);
        //设置6秒后是否自动锁车。
        options.setAutoLockCar(true);
        return options;
    }

    /**
     * 生成一个RouteOverlayOptions
     * 设置拥堵路线
     *
     * @return
     */
    public static RouteOverlayOptions generateOptions(Resources resources) {

        //请一定这样加载图片 不然地图上的路线会不清楚。(谨记)
        //默认的路线纹理位图和交通状况未知下的纹理位图
        BitmapDescriptor unknownTraffic = BitmapDescriptorFactory.fromResource(R.drawable.custtexture_no);
        //路线中白色小箭头图片
        BitmapDescriptor arrowOnRoute = BitmapDescriptorFactory.fromResource(R.drawable.custtexture_aolr);
        //交通状况情况良好下的纹理位图
        BitmapDescriptor smoothTraffic = BitmapDescriptorFactory.fromResource(R.drawable.custtexture_green);
        //交通状况迟缓下的纹理位图
        BitmapDescriptor slowTraffic = BitmapDescriptorFactory.fromResource(R.drawable.custtexture_slow);
        //交通状况拥堵下的纹理位图
        BitmapDescriptor jamTraffic = BitmapDescriptorFactory.fromResource(R.drawable.custtexture_bad);
        //交通状况非常拥堵下的纹理位图
        BitmapDescriptor veryJamTraffic = BitmapDescriptorFactory.fromResource(R.drawable.custtexture_grayred);
        RouteOverlayOptions options = new RouteOverlayOptions();
        //设置绘制路线宽度 要根据手机密度来设置
        //否则 有些手机显示的路线太小
        options.setLineWidth(24 * resources.getDisplayMetrics().density);


        //设置默认的路线纹理位图（未开启路况时）。
        options.setNormalRoute(unknownTraffic.getBitmap());

        //设置路线中白色小箭头图片
        options.setArrowOnTrafficRoute(arrowOnRoute.getBitmap());

        //设置交通状况情况良好下的纹理位图
        options.setSmoothTraffic(smoothTraffic.getBitmap());

        //设置交通状况未知下的纹理位图
        options.setUnknownTraffic(unknownTraffic.getBitmap());

        //设置交通状况迟缓下的纹理位图
        options.setSlowTraffic(slowTraffic.getBitmap());

        //设置交通状况拥堵下的纹理位图
        options.setJamTraffic(jamTraffic.getBitmap());

        //设置交通状况非常拥堵下的纹理位图
        options.setVeryJamTraffic(veryJamTraffic.getBitmap());

        //设置转弯的箭头颜色
        // options.setArrowColor(resources.getColor(R.color.white));

        return options;

    }
}
