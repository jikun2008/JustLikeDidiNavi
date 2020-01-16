package com.justlike.navi.didi.navi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void toNaviActivity(View view){
        Intent intent=new Intent(this,NaviActivity.class);
        startActivity(intent);

    }

    public void toNaviRouteActivity(View view) {
        //天府三街
        Poi start = new Poi("", TestConstant.start, "");
        //天府广场
        Poi end = new Poi("", TestConstant.end,"");
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(start, null, end, AmapNaviType.DRIVER, AmapPageType.NAVI), null);
    }
}
