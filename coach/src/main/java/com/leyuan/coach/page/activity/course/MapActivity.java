package com.leyuan.coach.page.activity.course;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.page.BaseActivity;

/**
 * Created by user on 2016/12/26.
 */
public class MapActivity extends BaseActivity {

    private ImageView imgLeft;
    private TextView txtDate;
    private TextView txtTime;
    private MapView mapView;
    private ClassSchedule classSchedule;
    private BaiduMap mMap;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle bundle =  getIntent().getExtras();
        if(bundle!= null){
            classSchedule =bundle.getParcelable(Constant.CLASS_SCHEDULE);
            currentDate =  bundle.getString(Constant.CURRENT_DATE);
        }


        imgLeft = (ImageView) findViewById(R.id.img_left);
        txtDate = (TextView) findViewById(R.id.txt_date);
        txtTime = (TextView) findViewById(R.id.txt_time);
        mapView = (MapView) findViewById(R.id.mapView);

        initBaiduMap();
        initData();
    }

    private void initBaiduMap() {
        mMap = mapView.getMap();
        mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        LatLng point = new LatLng(classSchedule.getLat(), classSchedule.getLng());

        mMap.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(point, 19));
        View view = View.inflate(this, R.layout.layout_market_map, null);

        TextView txtStoreName = (TextView) view.findViewById(R.id.txt_store_name);
        TextView txtStoreAddress = (TextView) view.findViewById(R.id.txt_store_address);
        txtStoreName.setText(classSchedule.getStoreName());
        txtStoreAddress.setText(classSchedule.getAddress());

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromView(view);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        mMap.addOverlay(option);
    }

    private void initData() {
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtDate.setText(currentDate+"");
        txtTime.setText(classSchedule.getBeginTime()+"-"+classSchedule.getEndTime());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
