package com.justlike.navi.didi.navi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.amap.api.location.DPoint;
import com.amap.api.navi.model.NaviLatLng;
import com.yisingle.navi.library.data.NaviActionData;
import com.yisingle.navi.library.fragment.NaviFragment;

public class NaviActivity extends AppCompatActivity {
    private FrameLayout naviLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);


        DPoint dPoint = AMapConverterUtils.convert(getApplicationContext(), TestConstant.start.latitude,TestConstant.start.longitude);

        DPoint dPoint1 = AMapConverterUtils.convert(getApplicationContext(),  TestConstant.end.latitude, TestConstant.end.longitude);
        NaviLatLng start = new NaviLatLng(dPoint.getLatitude(), dPoint.getLongitude());

        NaviLatLng end = new NaviLatLng(dPoint1.getLatitude(), dPoint1.getLongitude());

        Log.e("测试代码", "start测试代码getLatitude=" + start.getLatitude() + "getLongitude=" + start.getLongitude());

        Log.e("测试代码", "end测试代码getLatitude=" + end.getLatitude() + "getLongitude=" + end.getLongitude());

        NaviActionData naviActionData = new NaviActionData.Builder()
                //设置模拟导航
                .setEmulatorNavi(true)
                .setStartLatlng(start)
                .build(end);


        NaviFragment naviFragment = NaviFragment.newInstance(naviActionData);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.naviLayout, naviFragment)
                .commit();
    }
}
