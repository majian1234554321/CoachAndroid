package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.MessageInfo;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.adapter.MessageCenterAdapter;
import com.leyuan.coach.page.mvp.presenter.MessagePresenter;
import com.leyuan.coach.page.mvp.view.MessageViewListener;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.UiManager;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/29.
 */
public class MessageCenterActivity extends BaseActivity implements MessageCenterAdapter.OnItemClickListener, MessageViewListener {

    private CommonTitleLayout layoutTitle;
    private RecyclerView recyclerView;
    private MessageCenterAdapter adapter;
    private MessagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new MessageCenterAdapter(this);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        presenter = new MessagePresenter(this, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getMsgList();
    }

    @Override
    public void onMessageClick(int messageId) {
        presenter.updateMsgStatus(String.valueOf(messageId));
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.MESSAGE_ID, messageId);
        UiManager.activityJump(this, bundle, MessageDetailActivity.class);
    }

    @Override
    public void onGetMsgListResult(ArrayList<MessageInfo> messageInfos) {
        adapter.refreshData(messageInfos);

    }
}
