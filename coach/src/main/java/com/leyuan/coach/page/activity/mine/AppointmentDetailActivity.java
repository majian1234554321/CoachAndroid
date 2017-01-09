package com.leyuan.coach.page.activity.mine;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.AppointmentDetailBean;
import com.leyuan.coach.bean.PayOrderBean;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.activity.train.AppointSuccessActivity;
import com.leyuan.coach.page.activity.train.TrainDetailActivity;
import com.leyuan.coach.page.mvp.presenter.AppointmentPresent;
import com.leyuan.coach.page.mvp.presenter.CampaignPresent;
import com.leyuan.coach.page.mvp.view.AppointmentDetailViewListener;
import com.leyuan.coach.pay.AliPay;
import com.leyuan.coach.pay.PayInterface;
import com.leyuan.coach.pay.WeiXinPay;
import com.leyuan.coach.utils.DensityUtil;
import com.leyuan.coach.utils.QRCodeUtil;
import com.leyuan.coach.widget.CustomNestRadioGroup;
import com.leyuan.coach.widget.ExtendTextView;
import com.leyuan.coach.widget.SimpleTitleBar;
import com.leyuan.coach.widget.SwitcherLayout;


public class AppointmentDetailActivity extends BaseActivity implements View.OnClickListener, CustomNestRadioGroup.OnCheckedChangeListener,AppointmentDetailViewListener {
    private static final String UN_PAID = "0";          //待付款
    private static final String UN_JOIN= "1";           //待参加
    private static final String CLOSE = "2";            //已关闭
    private static final String JOINED = "3";           //已参加
    private static final String PAY_ALI = "1";
    private static final String PAY_WEIXIN = "2";

    private SwitcherLayout switcherLayout;
    private SimpleTitleBar titleBar;
    private ScrollView scrollView;
    private TextView tvState;
    private TextView tvTimeOrNum;
    private RelativeLayout rlDetail;
    private SimpleDraweeView dvGoodsCover;
    private TextView tvName;
    private TextView tvInfo;
    private RelativeLayout codeLayout;
    private TextView tvNum;
    private SimpleDraweeView dvQr;
    private LinearLayout llCampaignInfo;
    private ExtendTextView tvAppointUser;
    private ExtendTextView tvUserPhone;
    private ExtendTextView tvCampaignTime;
    private ExtendTextView tvCampaignAddress;
    private CustomNestRadioGroup radioGroup;
    private RadioButton rbALiPay;
    private RadioButton rbWeiXinPay;
    private TextView tvGoodsCount;
    private TextView tvPayTip;
    private TextView tvPrice;
    private TextView tvCancel;
    private TextView tvPay;
    private TextView tvExpress;
    private TextView tvReceiving;
    private TextView tvDelete;
    private TextView tvAgainBuy;

    private String orderId;
    private String payType;
    private AppointmentDetailBean detailBean;
    private CampaignPresent campaignPresent;
    private AppointmentPresent appointmentPresent;


    public static void start(Context context,String orderId) {
        Intent starter = new Intent(context, AppointmentDetailActivity.class);
        starter.putExtra("orderId",orderId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        campaignPresent = new CampaignPresent(this,this);
        appointmentPresent = new AppointmentPresent(this,this);
        if(getIntent() != null){
            orderId = getIntent().getStringExtra("orderId");
        }
        initView();
        setListener();
        appointmentPresent.getAppointmentDetail(switcherLayout,orderId);
    }

    private void initView(){
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        switcherLayout = new SwitcherLayout(this,scrollView);
        tvState = (TextView) findViewById(R.id.tv_state);
        tvTimeOrNum = (TextView) findViewById(R.id.tv_time_or_num);
        rlDetail = (RelativeLayout) findViewById(R.id.rl_detail);
        dvGoodsCover = (SimpleDraweeView) findViewById(R.id.dv_goods_cover);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        codeLayout = (RelativeLayout) findViewById(R.id.rl_qr_code);
        tvNum = (TextView) findViewById(R.id.tv_num);
        dvQr = (SimpleDraweeView) findViewById(R.id.dv_qr);
        llCampaignInfo = (LinearLayout) findViewById(R.id.ll_campaign_info);
        tvAppointUser = (ExtendTextView) findViewById(R.id.tv_appoint_user);
        tvUserPhone = (ExtendTextView) findViewById(R.id.tv_user_phone);
        tvCampaignTime = (ExtendTextView) findViewById(R.id.tv_campaign_time);
        tvCampaignAddress = (ExtendTextView) findViewById(R.id.tv_campaign_address);
        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        rbALiPay = (RadioButton) findViewById(R.id.cb_alipay);
        rbWeiXinPay = (RadioButton) findViewById(R.id.cb_weixin);
        tvGoodsCount = (TextView) findViewById(R.id.tv_goods_count);
        tvPayTip = (TextView) findViewById(R.id.tv_pay_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        tvExpress = (TextView) findViewById(R.id.tv_express);
        tvReceiving = (TextView) findViewById(R.id.tv_receiving);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvAgainBuy = (TextView) findViewById(R.id.tv_again_buy);
    }

    private void setListener(){
        titleBar.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        rlDetail.setOnClickListener(this);
        tvPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_detail:
                TrainDetailActivity.start(this,detailBean.getCampaign().getCampaignId());
                break;
            case R.id.tv_pay:
                if(payType.equals(detailBean.getCampaign().getPayType())) {
                    PayInterface payInterface = (detailBean.getCampaign().getPayType().equals("1")) ?
                            new AliPay(this, payListener) : new WeiXinPay(this, payListener);
                    PayOrderBean payOrderBean = new PayOrderBean();
                    payOrderBean.setPayOption(detailBean.getPayOption());
                    payInterface.payOrder(payOrderBean);
                }else {
                    campaignPresent.changePayType(orderId,payType);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setChangePayType(PayOrderBean payOrderBean) {
        PayInterface payInterface = (payOrderBean.getPayType().equals("1")) ?
                new AliPay(this, payListener) : new WeiXinPay(this, payListener);
        payInterface.payOrder(payOrderBean);
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
                    break;
            }
            Toast.makeText(AppointmentDetailActivity.this,tip,Toast.LENGTH_LONG).show();
        }

        @Override
        public void success(String code, Object object) {
            Toast.makeText(AppointmentDetailActivity.this,"支付成功",Toast.LENGTH_LONG).show();
            startActivity(new Intent(AppointmentDetailActivity.this,AppointSuccessActivity.class));
        }
    };


    @Override
    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.cb_alipay:
                payType = PAY_ALI;
                break;
            case R.id.cb_weixin:
                payType = PAY_WEIXIN;
                break;
            default:
                break;
        }
    }

    @Override
    public void setAppointmentDetail(AppointmentDetailBean bean) {
        detailBean = bean;
        payType = bean.getCampaign().getPayType();
        if(PAY_ALI.equals(payType)){
            rbALiPay.setChecked(true);
        }else {
            rbWeiXinPay.setChecked(true);
        }

        //与订单状态无关: 订单信息
        dvGoodsCover.setImageURI(bean.getCampaign().getCampImg());
        tvName.setText(bean.getCampaign().getTitle());
        tvInfo.setText(bean.getCampaign().getBrandName());
        tvNum.setText(bean.getCampaign().getOrderId());
        dvQr.setImageBitmap(QRCodeUtil.creatBarcode(this,bean.getCampaign().getOrderId(),
                DensityUtil.dp2px(this,294),DensityUtil.dp2px(this,73),true));

        tvAppointUser.setRightContent(bean.getCampaign().getUserName());
        tvUserPhone.setRightContent(bean.getCampaign().getPhone());
        tvCampaignTime.setRightContent(bean.getCampaign().getStartDate());
        tvCampaignAddress.setRightContent(bean.getCampaign().getAddress());

        tvPrice.setText(bean.getCampaign().getPayAmount());

        //与订单状态有关: 预约状态信息 课程预约信息/活动预约信息 支付方式信息 底部预约操作状态及价格信息
        switch (bean.getCampaign().getStatus()){
            case UN_PAID:
                tvState.setText(getString(R.string.un_paid));
                tvTimeOrNum.setText(String.format(getString(R.string.order_pay_time),bean.getCampaign().getLittleTime()));
                codeLayout.setVisibility(View.GONE);
                break;
            case UN_JOIN:
                tvState.setText(getString(R.string.appointment_un_joined));
                tvTimeOrNum.setText(String.format(getString(R.string.order_no),bean.getCampaign().getOrderId()));
                codeLayout.setVisibility(View.VISIBLE);
                break;
            case JOINED:
                tvState.setText(getString(R.string.appointment_joined));
                tvTimeOrNum.setText(String.format(getString(R.string.order_no),bean.getCampaign().getOrderId()));
                codeLayout.setVisibility(View.VISIBLE);
                break;
            case CLOSE:
                tvState.setText(getString(R.string.order_close));
                tvTimeOrNum.setText(String.format(getString(R.string.order_no),bean.getCampaign().getOrderId()));
                codeLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
