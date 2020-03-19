package com.jaayu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

public class CustomSlider extends BaseSliderView {

    ImageView target;
    private Activity activity;
    public CustomSlider(Context context) {
        super(context);
        this.activity = (Activity) context;
    }

    @Override
    public View getView() {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.home_slider, null);
        target = (ImageView) v.findViewById(R.id.image);


        bindEventAndShow(v, target);
        return v;
    }

}