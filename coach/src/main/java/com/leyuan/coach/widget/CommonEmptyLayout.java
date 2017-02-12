package com.leyuan.coach.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.coach.R;

/**
 * Created by user on 2017/2/12.
 */

public class CommonEmptyLayout extends LinearLayout {
    private ImageView imgEmpty;
    private TextView txtEmpty;

    public CommonEmptyLayout(Context context) {
        this(context, null);
    }

    public CommonEmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonEmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.empty_view, this);
        imgEmpty = (ImageView) findViewById(R.id.img_empty);
        txtEmpty = (TextView) findViewById(R.id.txt_empty);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonEmptyLayout, defStyleAttr, 0);

        String desc = typedArray.getString(R.styleable.CommonEmptyLayout_txtEmpty);
        if (desc != null)
            txtEmpty.setText(desc);
        imgEmpty.setImageDrawable(typedArray.getDrawable(R.styleable.CommonEmptyLayout_imgEmpty));
        typedArray.recycle();
    }
}
