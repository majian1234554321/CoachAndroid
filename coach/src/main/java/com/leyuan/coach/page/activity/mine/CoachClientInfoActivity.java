package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.VersionManager;

/**
 * Created by user on 2017/1/12.
 */
public class CoachClientInfoActivity extends BaseActivity {
    private CommonTitleLayout layoutRoot;
    private TextView txtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_client_info);

        layoutRoot = (CommonTitleLayout) findViewById(R.id.layout_root);
        txtVersion = (TextView) findViewById(R.id.txt_version);

        txtVersion.setText("v " + VersionManager.getVersionName(this));
        layoutRoot.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
