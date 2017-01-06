package com.leyuan.coach.page.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.CoachInfo;
import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.BaseFragment;
import com.leyuan.coach.page.activity.mine.SettingActivity;
import com.leyuan.coach.page.activity.mine.MessageCenterActivity;
import com.leyuan.coach.page.activity.course.NextMonthClassScheduleActivity;
import com.leyuan.coach.page.activity.mine.MyMoneyActivity;
import com.leyuan.coach.page.activity.mine.SignInRecordActivity;
import com.leyuan.coach.page.activity.mine.UserInfoActivity;
import com.leyuan.coach.page.mvp.presenter.MinePresenter;
import com.leyuan.coach.page.mvp.view.MineViewListener;
import com.leyuan.commonlibrary.manager.UiManager;

/**
 * Created by user on 2016/12/19.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener, MineViewListener {

    private ImageView imgClassWarn;
    private SimpleDraweeView imgAvatar;
    private TextView txtName;
    private ImageView imgCenterDivider;
    private TextView txtAttendanceRate;
    private TextView txtStarLevel;
    private TextView txt_next_month_class;
    private RelativeLayout layoutMineMoney;
    private RelativeLayout layoutMyAppointment;
    private RelativeLayout layoutMessage;
    private ImageView imgNewMessage;
    private RelativeLayout layoutMySignIn;
    private RelativeLayout layoutSetting;

    private MinePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgClassWarn = (ImageView) view.findViewById(R.id.img_class_warn);
        imgAvatar = (SimpleDraweeView) view.findViewById(R.id.img_avatar);
        txtName = (TextView) view.findViewById(R.id.txt_name);
        txtAttendanceRate = (TextView) view.findViewById(R.id.txt_attendance_rate);
        txtStarLevel = (TextView) view.findViewById(R.id.txt_star_level);
        imgNewMessage = (ImageView) view.findViewById(R.id.img_new_message);
        txt_next_month_class = (TextView) view.findViewById(R.id.txt_next_month_class);

        view.findViewById(R.id.layout_mine_money).setOnClickListener(this);
        view.findViewById(R.id.layout_my_appointment).setOnClickListener(this);
        view.findViewById(R.id.layout_message).setOnClickListener(this);
        view.findViewById(R.id.layout_my_sign_in).setOnClickListener(this);
        view.findViewById(R.id.layout_setting).setOnClickListener(this);
        imgAvatar.setOnClickListener(this);
        txtName.setOnClickListener(this);
        txt_next_month_class.setOnClickListener(this);

        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_avatar:
                UiManager.activityJump(getActivity(), UserInfoActivity.class);
                break;
            case R.id.txt_name:
                UiManager.activityJump(getActivity(), UserInfoActivity.class);
                break;
            case R.id.txt_next_month_class:
                UiManager.activityJump(getActivity(), NextMonthClassScheduleActivity.class);
                break;
            case R.id.layout_mine_money:
                UiManager.activityJump(getActivity(), MyMoneyActivity.class);
                break;
            case R.id.layout_my_appointment:

                break;
            case R.id.layout_message:
                UiManager.activityJump(getActivity(), MessageCenterActivity.class);
                break;
            case R.id.layout_my_sign_in:
                UiManager.activityJump(getActivity(), SignInRecordActivity.class);


                break;
            case R.id.layout_setting:
                UiManager.activityJump(getActivity(), SettingActivity.class);
                break;
        }
    }

    private void initData() {
        UserCoach user = App.getInstance().getUser();
        imgAvatar.setImageURI(user.getAvatar());
        txtName.setText(user.getName() + "");


        presenter = new MinePresenter(getActivity(), this);
        presenter.getUserInfo("" + user.getId());
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void getUserInfo(CoachInfo userInfo) {
        if (userInfo != null) {
            imgClassWarn.setVisibility(userInfo.getNextCou() == 1 ? View.VISIBLE : View.GONE);
            txtStarLevel.setText("评价星级 " + userInfo.getRated());
            txtAttendanceRate.setText("当月出勤率" + userInfo.getTimeCard());
            imgNewMessage.setVisibility(userInfo.getMsgCou() == 1 ? View.VISIBLE : View.GONE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
