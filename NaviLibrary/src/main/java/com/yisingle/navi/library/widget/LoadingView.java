package com.yisingle.navi.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yisingle.navi.library.R;


/**
 * @author jikun
 *         Created by jikun on 2018/3/2.
 *         用来在路径规划中显示loading  成功 失败的 View
 */

public class LoadingView extends RelativeLayout {


    private ProgressBar progressBar;

    private TextView tvProgressInfo;
    private Button btError;

    private OnClickListener clickListener;


    public LoadingView(Context context) {
        super(context);
        initView(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    public void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_route_loading, null);
        progressBar = view.findViewById(R.id.progressBar);
        tvProgressInfo = view.findViewById(R.id.tvProgressInfo);
        btError = view.findViewById(R.id.btError);
        addView(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        btError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(btError);
                }
            }
        });
    }

    public void showLoading(String info) {
        setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        tvProgressInfo.setVisibility(View.VISIBLE);
        tvProgressInfo.setText(info);
        btError.setVisibility(View.GONE);

    }

    public void showSuccess() {
        setVisibility(View.GONE);
    }


    public void showErrorInfo() {
        showErrorInfo("");
    }

    public void showErrorInfo(String info) {
        setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvProgressInfo.setVisibility(View.GONE);
        btError.setVisibility(View.VISIBLE);
        btError.setText(info);
    }

    public void setOnErrorClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;

    }


}
