package com.leyuan.coach.page.activity.train;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.CampaignDetailBean;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.mvp.presenter.CampaignPresent;
import com.leyuan.coach.pay.PayInterface;
import com.leyuan.coach.widget.CustomNestRadioGroup;
import com.leyuan.coach.widget.SimpleTitleBar;

public class AppointTrainActivity extends BaseActivity implements View.OnClickListener, CustomNestRadioGroup.OnCheckedChangeListener {
    private static final String ALI_PAY = "1";
    private static final String WEI_XIN_PAY = "2";
    private SimpleTitleBar titleBar;
    private TextView tvInputName;
    private TextView tvInputPhone;
    private SimpleDraweeView dvCover;
    private TextView tvName;
    private TextView tvShop;
    private TextView tvTime;
    private TextView tvAddress;
    private CustomNestRadioGroup radioGroup;
    private TextView tvTip;
    private TextView tvPrice;
    private TextView tvPay;

    private String campaignId;
    private String coachId;
    private String payType;
    private CampaignDetailBean detailBean;
    private CampaignPresent campaignPresent;

    public static void start(Context context, CampaignDetailBean detailBean) {
        Intent starter = new Intent(context, AppointTrainActivity.class);
        starter.putExtra("detailBean",detailBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_appoint_train);
        campaignPresent = new CampaignPresent(this);
        coachId = String.valueOf(App.getInstance().getUser().getId());
        if(getIntent() != null){
            detailBean = getIntent().getParcelableExtra("detailBean");
        }
        initView();
        setListener();
    }

    private void initView(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tvInputName = (TextView) findViewById(R.id.tv_input_name);
        tvInputPhone = (TextView) findViewById(R.id.tv_input_phone);
        dvCover = (SimpleDraweeView) findViewById(R.id.dv_cover);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvShop = (TextView) findViewById(R.id.tv_shop);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        tvInputName.setText(App.getInstance().getUser().getName());
        tvInputPhone.setText(App.getInstance().getUser().getContact());
        dvCover.setImageURI(detailBean.getCamImg());
        tvName.setText(detailBean.getTitle());
        tvShop.setText(detailBean.getBrandName());
        tvTime.setText(detailBean.getSignStartTime());
        tvAddress.setText(detailBean.getPlace());
        campaignId = detailBean.getCampaignId();
        payType = ALI_PAY;
    }

    private void setListener() {
        tvPay.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar:
                finish();
                break;
            case R.id.tv_pay:
                campaignPresent.buyCampaign(campaignId,coachId,payType,payListener);
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new PayInterface.PayListener() {
        @Override
        public void fail(String code, Object object) {
            String tip = "";
            switch (code){
                case "4000":
                    tip = "订单支付失败";
                    break;
                case "5000":
                    tip = "订单重复提交";
                    break;
                case "6001":
                    tip = "订单取消支付";
                    break;
                case "6002":
                    tip = "网络连接出错";
                    break;
                default:
                    tip = code +":::"+ object.toString();
                    break;
            }
            Toast.makeText(AppointTrainActivity.this,tip,Toast.LENGTH_LONG).show();
        }

        @Override
        public void success(String code, Object object) {
            Toast.makeText(AppointTrainActivity.this,"支付成功啦啦啦啦啦绿",Toast.LENGTH_LONG).show();
            //startActivity(new Intent(AppointTrainActivity.this,AppointSuccessActivity.class));
        }
    };

    @Override
    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.cb_alipay:
                payType = ALI_PAY;
                break;
            case R.id.cb_weixin:
                payType = WEI_XIN_PAY;
                break;
            default:
                break;
        }
    }
}
