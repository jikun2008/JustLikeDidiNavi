package com.justlike.navi.didi.navi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.amap.api.navi.model.NaviLatLng;
import com.yisingle.navi.library.data.NaviActionData;
import com.yisingle.navi.library.fragment.NaviFragment;

public class NaviActivity extends AppCompatActivity {
    private FrameLayout naviLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);


        NaviActionData naviActionData = new NaviActionData.Builder()
                //设置模拟导航
                .setEmulatorNavi(true)
                .setStartLatlng(new NaviLatLng(30.632952,103.994195))
                .build(new NaviLatLng(30.583347,104.068396));


        NaviFragment naviFragment = NaviFragment.newInstance(naviActionData);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.naviLayout, naviFragment)
                .commit();
    }
}
