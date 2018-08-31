package com.yisingle.navi.library.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.amap.api.navi.model.NaviLatLng;

/**
 * @author jikun
 * Created by jikun on 2018/4/3.
 */

public class NaviActionData implements Parcelable {

    private NaviActionData() {
    }

    /**
     * 乘客设置起点
     */
    private NaviLatLng startLatlng;

    /**
     * 乘客设置终点
     */
    private NaviLatLng endLatlng;


    private boolean isEmulatorNavi = false;


    public NaviLatLng getStartLatlng() {
        return startLatlng;
    }

    public void setStartLatlng(NaviLatLng startLatlng) {
        this.startLatlng = startLatlng;
    }

    public NaviLatLng getEndLatlng() {
        return endLatlng;
    }

    public void setEndLatlng(NaviLatLng endLatlng) {
        this.endLatlng = endLatlng;
    }


    public boolean isEmulatorNavi() {
        return isEmulatorNavi;
    }

    public void setEmulatorNavi(boolean emulatorNavi) {
        isEmulatorNavi = emulatorNavi;
    }

    public static class Builder {


        private NaviActionData data;


        public Builder() {
            data = new NaviActionData();
        }


        public Builder setStartLatlng(@Nullable NaviLatLng startLatlng) {
            data.setStartLatlng(startLatlng);
            return this;
        }

        public Builder setEndLatlng(@NonNull NaviLatLng endLatlng) {
            data.setEndLatlng(endLatlng);
            return this;
        }

        public Builder setEmulatorNavi(boolean emulatorNavi) {
            data.setEmulatorNavi(emulatorNavi);
            return this;
        }


        public NaviActionData build(@NonNull NaviLatLng endLatlng) {
            data.setEndLatlng(endLatlng);
            return data;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.startLatlng, flags);
        dest.writeParcelable(this.endLatlng, flags);
    }

    protected NaviActionData(Parcel in) {
        this.startLatlng = in.readParcelable(NaviLatLng.class.getClassLoader());
        this.endLatlng = in.readParcelable(NaviLatLng.class.getClassLoader());
    }

    public static final Parcelable.Creator<NaviActionData> CREATOR = new Parcelable.Creator<NaviActionData>() {
        @Override
        public NaviActionData createFromParcel(Parcel source) {
            return new NaviActionData(source);
        }

        @Override
        public NaviActionData[] newArray(int size) {
            return new NaviActionData[size];
        }
    };
}
