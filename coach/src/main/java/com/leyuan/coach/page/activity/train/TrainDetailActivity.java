package com.leyuan.coach.page.activity.train;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.CampaignDetailBean;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.activity.mine.AppointmentDetailActivity;
import com.leyuan.coach.page.adapter.ApplicantAdapter;
import com.leyuan.coach.page.mvp.presenter.CampaignPresent;
import com.leyuan.coach.page.mvp.view.TrainDetailViewListener;
import com.leyuan.coach.widget.SwitcherLayout;
import com.zzhoujay.richtext.RichText;

public class TrainDetailActivity extends BaseActivity implements View.OnClickListener ,TrainDetailViewListener {
    public static final String STATUS_APPLY = "0";                //马上报名
    public static final String STATUS_CAMPAIGN_END = "1";         //活动已结束
    public static final String STATUS_APPLY_END = "2";            //报名结束
    public static final String STATUS_NOT_START = "3";            //即将开始报名
    public static final String STATUS_NOT_PAY = "4";              //待支付
    public static final String STATUS_APPLIED = "5";              //已报名
    public static final String STATUS_FULL = "6";                 //报名人数已满
    public static final String STATUS_NO_PERMISSION_APPLY = "7";  //无资格报名
    private String status;

    private SwitcherLayout switcherLayout;
    private TextView tvTitle;
    private ImageView ivBack;
    private TextView tvStartTimeTip;
    private LinearLayout llContent;
    private SimpleDraweeView dvCover;
    private TextView tvCampaignName;
    private TextView tvLandmark;
    private TextView tvDate;
    private TextView tvTime;
    private LinearLayout llAddress;
    private TextView tvAddress;
    private TextView tvOrganizer;
    private TextView tvCoachLevel;
    private TextView tvCount;
    private RecyclerView applicantView;
    private TextView tvCampaignDesc;
    private LinearLayout bottomLayout;
    private TextView tvPrice;
    private TextView tvState;

    private int coachId;
    private String campaignId ;
    private String orderId;
    private ApplicantAdapter applicantAdapter;
    private CampaignDetailBean detailBean;
    private CampaignPresent campaignPresent;


    public static void start(Context context,String campaignId) {
        Intent starter = new Intent(context, TrainDetailActivity.class);
        starter.putExtra("campaignId",campaignId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_detail);
        campaignPresent = new CampaignPresent(this,this);
        Intent intent = getIntent();
        if(intent != null){
            campaignId = intent.getStringExtra("campaignId");
        }
        coachId = App.getInstance().getUser().getId();
        initView();
        setListener();
        campaignPresent.getCampaignDetail(switcherLayout,campaignId,String.valueOf(coachId));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichText.clear(this);
    }

    private void initView(){
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvStartTimeTip = (TextView) findViewById(R.id.tv_start_time);
        llContent = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this,llContent);
        dvCover = (SimpleDraweeView) findViewById(R.id.dv_cover);
        tvCampaignName = (TextView) findViewById(R.id.tv_campaign_name);
        tvLandmark = (TextView) findViewById(R.id.tv_landmark);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvTime = (TextView) findViewById(R.id.tv_time);
        llAddress = (LinearLayout) findViewById(R.id.ll_address);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvOrganizer = (TextView) findViewById(R.id.tv_organizer);
        tvCoachLevel = (TextView) findViewById(R.id.tv_coach_level);
        tvCount = (TextView) findViewById(R.id.tv_count);
        applicantView = (RecyclerView) findViewById(R.id.rv_applicant);
        tvCampaignDesc = (TextView) findViewById(R.id.tv_campaign_desc);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_apply);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvState = (TextView) findViewById(R.id.tv_state);
        applicantView.setLayoutManager(new LinearLayoutManager
                (this,LinearLayoutManager.HORIZONTAL,false));
        applicantAdapter = new ApplicantAdapter();
        applicantView.setAdapter(applicantAdapter);
        applicantView.setNestedScrollingEnabled(false);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
        tvCount.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_count:
                AppointmentUserActivity.start(this,detailBean.getMembersList());
                break;
            case R.id.ll_apply:
                if(STATUS_APPLY.equals(status)){     //预约
                    Intent i = new Intent(this,AppointTrainActivity.class);
                    i.putExtra("detailBean",detailBean);
                    startActivityForResult(i,0);
                }else if(STATUS_NOT_PAY.equals(status)){
                    AppointmentDetailActivity.start(this,orderId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setCampaignDetail(CampaignDetailBean bean) {
        bottomLayout.setVisibility(View.VISIBLE);
        this.detailBean = bean;
        orderId = detailBean.getOrderId();
        status = bean.getStatus();
        tvTitle.setText(bean.getTitle());
        dvCover.setImageURI(bean.getCamImg());
        tvCampaignName.setText(bean.getSubtitle());
        tvLandmark.setText(bean.getAreaName());
        tvDate.setText(bean.getStartDate());
        tvTime.setText(bean.getStartTime());
        tvAddress.setText(bean.getPlace());
        tvOrganizer.setText(bean.getBrandName());
        tvCoachLevel.setText(bean.getCoachLevel());
        applicantAdapter.setData(bean.getMembersList());
        tvCount.setText(String.format(getString(R.string.applicant_count),
                bean.getAlreadyPerson(),bean.getAllowPerson()));
        tvPrice.setText(String.format(getString(R.string.rmb_price),bean.getPrice()));
        tvStartTimeTip.setText(String.format(getString(R.string.appoint_time),bean.getSignStartTime()));
        RichText.from(bean.getContents()).into(tvCampaignDesc);
        setBottomStatus();
    }

    @Override
    public void shareCampaign() {

    }

    @Override
    public void applyCampaign() {

    }

    private void setBottomStatus(){
        if(TextUtils.isEmpty(status)) {
            return;
        }
        bottomLayout.setVisibility(View.VISIBLE);
        switch (status){
            case STATUS_APPLY:
                tvPrice.setVisibility(View.VISIBLE);
                tvStartTimeTip.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_apply);
                bottomLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case STATUS_CAMPAIGN_END:
                tvPrice.setVisibility(View.GONE);
                tvStartTimeTip.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_end);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_APPLY_END:
                tvPrice.setVisibility(View.GONE);
                tvStartTimeTip.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_apply_end);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_APPLIED:
                tvPrice.setVisibility(View.GONE);
                tvStartTimeTip.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_applied);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_NOT_START:
                tvPrice.setVisibility(View.GONE);
                tvStartTimeTip.setVisibility(View.VISIBLE);
                tvState.setText(R.string.campaign_status_not_start);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_NOT_PAY:
                tvPrice.setVisibility(View.VISIBLE);
                tvStartTimeTip.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_not_pay);
                bottomLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case STATUS_FULL:
                tvPrice.setVisibility(View.GONE);
                tvStartTimeTip.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_full);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_NO_PERMISSION_APPLY:
                tvPrice.setVisibility(View.GONE);
                tvStartTimeTip.setVisibility(View.GONE);
                tvState.setText(R.string.campaign_status_no_permission);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null && requestCode == 0){
            status = data.getStringExtra("status");
            orderId = data.getStringExtra("orderId");
            setBottomStatus();
        }
    }
}
