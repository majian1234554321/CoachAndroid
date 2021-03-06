package com.leyuan.coach.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.coach.R;


public class CommonTitleLayout extends RelativeLayout {

    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private RelativeLayout relTitle;

    public CommonTitleLayout(Context context) {
        this(context, null);
    }

    public CommonTitleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.common_title, this);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        imgRight = (ImageView) findViewById(R.id.img_right);
        txtRight = (TextView) findViewById(R.id.txt_right);
        relTitle = (RelativeLayout) findViewById(R.id.rel_title);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleLayout, defStyleAttr, 0);

        Drawable leftDrawable = typedArray.getDrawable(R.styleable.CommonTitleLayout_imgLeft);
        if (leftDrawable != null)
            imgLeft.setImageDrawable(leftDrawable);

        if(!typedArray.getBoolean(R.styleable.CommonTitleLayout_visible,true)){
            imgLeft.setVisibility(View.GONE);
        }

        txtTitle.setText(typedArray.getString(R.styleable.CommonTitleLayout_txtTitle));
        imgRight.setImageDrawable(typedArray.getDrawable(R.styleable.CommonTitleLayout_imgRight));
        txtRight.setText(typedArray.getString(R.styleable.CommonTitleLayout_txtRight));
        //        relTitle.setBackgroundColor(typedArray.getColor(R.styleable.CommonTitleLayout_titleBg, getResources().getColor(R.color.title_bg)));
        typedArray.recycle();
    }

    public void setLeftIconListener(OnClickListener leftIconListener) {
        imgLeft.setOnClickListener(leftIconListener);

    }

    public void setRightTextListener(OnClickListener rightTextListener) {
        txtRight.setOnClickListener(rightTextListener);
    }

    public void setleftIconVisible(boolean visible) {

        if (visible) {
            imgLeft.setVisibility(View.VISIBLE);
        } else {
            imgLeft.setVisibility(View.GONE);
        }

    }

}
