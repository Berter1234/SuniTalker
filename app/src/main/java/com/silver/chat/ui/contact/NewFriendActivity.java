package com.silver.chat.ui.contact;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silver.chat.AppContext;
import com.silver.chat.R;
import com.silver.chat.adapter.NewFriendAdapter;
import com.silver.chat.base.BaseActivity;
import com.silver.chat.database.dao.BaseDao;
import com.silver.chat.database.helper.DBHelper;
import com.silver.chat.database.info.WhereInfo;
import com.silver.chat.network.SSIMFrendManger;
import com.silver.chat.network.callback.ResponseCallBack;
import com.silver.chat.network.requestbean.AgreeFriendAddBody;
import com.silver.chat.network.responsebean.BaseResponse;
import com.silver.chat.network.responsebean.GroupMemberBean;
import com.silver.chat.network.responsebean.QueryUserInfoBean;
import com.silver.chat.util.PreferenceUtil;
import com.silver.chat.util.ToastUtils;
import com.silver.chat.view.recycleview.BaseQuickAdapter;
import com.silver.chat.view.recycleview.listenner.SimpleClickListener;
import com.ssim.android.engine.SSEngine;
import com.ssim.android.listener.SSNotificationListener;
import com.ssim.android.model.notification.SSFriendNotification;
import com.ssim.android.model.notification.SSNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：hibon on 2017/4/16 14:14
 * 新朋友
 */

public class NewFriendActivity extends BaseActivity implements View.OnClickListener, SSNotificationListener {

    private ImageView mBack;
    private RecyclerView mNFRecyclerList;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout mAddFriend;
    private TextView agreeAdd;
    private String token, userId, mNickName, mAvatar;
    private AgreeFriendAddBody agreeFriendAddBody;
    private List<SSFriendNotification> friendNotificationList;
    private List<SSFriendNotification> mUserList;
    private NewFriendAdapter addFriendAdatpter;
    private BaseDao<GroupMemberBean> mDao;
    private String sourceId ,content;
    private SSFriendNotification mSSFriendNotification;
    private QueryUserInfoBean queryUserInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friend;
    }

    @Override
    protected void initView() {
        super.initView();
        mBack = (ImageView) findViewById(R.id.title_left_back);
        mNFRecyclerList = (RecyclerView) findViewById(R.id.new_friend_list);
        mAddFriend = (LinearLayout) findViewById(R.id.ll_add_friend);
        agreeAdd = (TextView) findViewById(R.id.agree_add_friend);
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        /*设置布局管理器*/
        mNFRecyclerList.setLayoutManager(linearLayoutManager);
        mUserList = new ArrayList<SSFriendNotification>();
        mDao = DBHelper.get().dao(GroupMemberBean.class);
        queryUserInfoBean = new QueryUserInfoBean();

    }

    @Override
    protected void initData() {
        super.initData();
        token = PreferenceUtil.getInstance(mContext).getString(PreferenceUtil.TOKEN, "");
        userId = PreferenceUtil.getInstance(mContext).getString(PreferenceUtil.USERID, "");
        mNickName = PreferenceUtil.getInstance(mContext).getString(PreferenceUtil.NICKNAME, "");
        mAvatar = PreferenceUtil.getInstance(mContext).getString(PreferenceUtil.AVATAR, "");

        /**
         * SDK中取得好友添加申请通知列表
         */
        friendNotificationList = SSEngine.getInstance().getFriendNotificationList();
        /*查询数据库*/
        QueryDbParent();
        if (addFriendAdatpter == null) {
            addFriendAdatpter = new NewFriendAdapter(R.layout.item_new_friend, mUserList);
        }
        mNFRecyclerList.setAdapter(addFriendAdatpter);
    }

    /**
     * 查询群表中联系人
     */
    private void QueryDbParent() {
        mUserList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < friendNotificationList.size(); i++) {
                    sourceId = friendNotificationList.get(i).getSourceId();
                     content = friendNotificationList.get(i).getContent();

                    List<GroupMemberBean> userInfoList = mDao.query(WhereInfo.get().equal("userId", sourceId));
//                    Log.e("onMainThread", "data+" + userInfoList);
                    mSSFriendNotification = new SSFriendNotification();
                    if (userInfoList.isEmpty()) {
                        /*其次从网络获取数据*/
                        httpIdQueryList();
                    } else {
                        String mDaoUserId = String.valueOf(userInfoList.get(0).getUserId());
                        Log.e("mDao_nickName", userInfoList.get(0).getNickName());
                        mSSFriendNotification.setContent(content);
                        mSSFriendNotification.setSourceAvatar(userInfoList.get(0).getAvatar());
                        mSSFriendNotification.setSourceName(userInfoList.get(0).getNickName());
                        mUserList.add(mSSFriendNotification);
                        Log.e("a", sourceId + "/" + mDaoUserId + "/" + content);
                        handler.sendEmptyMessage(0);
                    }
                }
            }
        }).start();

    }

    /**
     * 通过好友Id获取好友信息
     */
    private void httpIdQueryList() {
        Log.e("httpIdQueryList", "*******"+content);
        SSIMFrendManger.idQueryUserInfo(mContext, token, sourceId, new ResponseCallBack<BaseResponse<QueryUserInfoBean>>() {
            @Override
            public void onSuccess(BaseResponse<QueryUserInfoBean> queryUserInfoBeanBaseResponse) {
                queryUserInfoBean = queryUserInfoBeanBaseResponse.data;
            }

            @Override
            public void onFailed(BaseResponse<QueryUserInfoBean> queryUserInfoBeanBaseResponse) {
                ToastUtils.showMessage(mContext, queryUserInfoBeanBaseResponse.getStatusMsg());
            }

            @Override
            public void onError() {
                ToastUtils.showMessage(mContext, "未获取到好友信息");
            }
        });

        if (queryUserInfoBean != null){
//            Log.e("queryUserInfoBean",queryUserInfoBean.getNickName());
            mSSFriendNotification.setContent(content);
            mSSFriendNotification.setSourceAvatar(queryUserInfoBean.getAvatar());
            mSSFriendNotification.setSourceName(queryUserInfoBean.getNickName());
            mUserList.add(mSSFriendNotification);

        }


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    addFriendAdatpter.setNewData(mUserList);
                    addFriendAdatpter.notifyDataSetChanged();
                    break;
            }

        }
    };


    @Override
    protected void initListener() {
        /* 通知的监听*/
        AppContext.getInstance().instance.setNotificationListener(this);
        mBack.setOnClickListener(this);
        mAddFriend.setOnClickListener(this);

        mNFRecyclerList.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle mBundle = new Bundle();
                mBundle.putString("sourceId", sourceId);
                startActivity(FriendApplyforActivity.class, mBundle);
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.agree_add_friend:
                        Log.e("agrreAdd", "666666");
                        agreeFriendAddBody = new AgreeFriendAddBody();
                        agreeFriendAddBody.setAppName("innerapp");
                        agreeFriendAddBody.setSourceId(userId);
                        agreeFriendAddBody.setSourceName(mNickName);
                        agreeFriendAddBody.setSourceAvatar(mAvatar);
                        agreeFriendAddBody.setTargetId(sourceId);
                        SSIMFrendManger.agreeFriend(mContext, token, agreeFriendAddBody, new ResponseCallBack<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse baseResponse) {
                                Log.e("onSuccess", baseResponse.toString());
                                ToastUtils.showMessage(mContext, baseResponse.getStatusMsg());
                            }

                            @Override
                            public void onFailed(BaseResponse baseResponse) {
                                Log.e("onSuccess", baseResponse.toString());
                            }

                            @Override
                            public void onError() {
                                Log.e("onError", "onError");
                            }
                        });
                        break;
                }
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_back:
                finish();
                break;
            case R.id.ll_add_friend:
                startActivity(AddFriendActivity.class);
                break;

        }
    }

    String NotificationContent, NotificationSourceId;

    /**
     * 监听好友申请
     *
     * @param ssNotification
     */
    @Override
    public void receiveNotification(SSNotification ssNotification) {
        if (ssNotification instanceof SSFriendNotification) {
         /*好友申请添加好友的通知监听*/
            SSFriendNotification ssFriendNotiFication = (SSFriendNotification) ssNotification;
            NotificationSourceId = ssFriendNotiFication.getContent();
            NotificationSourceId = ssFriendNotiFication.getSourceId();
            Log.e("申请加好友通知:", NotificationSourceId + "/" + NotificationSourceId);
            friendNotificationList.add(ssFriendNotiFication);
            /*查询数据库*/
//            QueryDbParent();
        }
    }

//    public List<GroupMemberBean> getSortData() {
//        List<GroupMemberBean> userInfoList = mDao.query(WhereInfo.get().equal("userId", sourceId));
//        Log.e("getSortData", userInfoList + "/" + sourceId);
//        return userInfoList;
//    }
}
