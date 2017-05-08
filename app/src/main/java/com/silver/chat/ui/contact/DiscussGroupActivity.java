package com.silver.chat.ui.contact;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.silver.chat.R;
import com.silver.chat.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by Joe on 2017/5/8.
 */
public class DiscussGroupActivity extends BaseActivity{
    ImageView titleLeftBack;
    ImageView imageSeach;
    ListView listView;
    RelativeLayout rlSeach;
    ImageView ivDiscussGroup;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_discussgroup;
    }

    @Override
    protected void initView() {
        titleLeftBack = (ImageView) findViewById(R.id.title_left_back);
        imageSeach = (ImageView) findViewById(R.id.image_seach);
        listView = (ListView) findViewById(R.id.listview);
        rlSeach = (RelativeLayout) findViewById(R.id.rl_seach);
        ivDiscussGroup = (ImageView)findViewById(R.id.iv_discussgroup);

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }
    @OnClick({R.id.title_left_back,R.id.rl_seach, R.id.iv_discussgroup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_back:
                finish();
                break;
            case R.id.rl_seach:
                startActivity(SearchContactActivity.class);
                break;
            case R.id.iv_discussgroup:
                startActivity(GroupChatActivity.class);
                break;

        }
    }
}
