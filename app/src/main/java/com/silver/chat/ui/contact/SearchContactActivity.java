package com.silver.chat.ui.contact;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.silver.chat.R;
import com.silver.chat.adapter.SearchContactAdapter;
import com.silver.chat.base.BaseActivity;
import com.silver.chat.view.SearchLayout;

import java.util.ArrayList;

/**
 * 作者：hibon on 2016/11/28 18:16
 * 联系人搜索
 */

public class SearchContactActivity extends BaseActivity implements SearchLayout.OnSearchClickListener {

    private SearchLayout mSearchLayout;
    private RecyclerView mRecycleContent;
    private SearchContactAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_contact;
    }

    @Override
    protected void initView() {
        super.initView();
        mSearchLayout = (SearchLayout) findViewById(R.id.search_layout);
        mRecycleContent = (RecyclerView) findViewById(R.id.recyle_content);
        mSearchLayout.setHint(R.string.hint_search_contact_name);
        mRecycleContent.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("i=" + i);
        }
        mAdapter = new SearchContactAdapter(R.layout.item_search_contact, list);
        mRecycleContent.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSearchLayout.setOnSearchClickListener(this);
    }

    @Override
    public void onBack() {
        finish();
    }
}