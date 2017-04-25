package com.silver.chat.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.silver.chat.R;
import com.silver.chat.entity.ApplicationData;
import com.silver.chat.entity.ChatEntity;
import com.silver.chat.entity.User;
import com.silver.chat.view.recycleview.BaseQuickAdapter;
import com.silver.chat.view.recycleview.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */
//
public class ChatMessageAdapter extends BaseQuickAdapter<ChatEntity, BaseViewHolder> {


    public ChatMessageAdapter(int layoutResId, List<ChatEntity> data) {
        super(layoutResId, data);
    }

    public ChatMessageAdapter(List<ChatEntity> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder holper, ChatEntity item) {

        RelativeLayout leftLayout;
        RelativeLayout rightLayout;
        TextView leftMessageView;
        TextView rightMessageView;
        TextView timeView;
        ImageView leftPhotoView;
        ImageView rightPhotoView;

        leftLayout = holper.getView(R.id.chat_friend_left_layout);
        rightLayout = holper.getView(R.id.chat_user_right_layout);
        timeView = holper.getView(R.id.message_time);
        leftPhotoView = holper.getView(R.id.message_friend_userphoto);
        rightPhotoView = holper.getView(R.id.message_user_userphoto);
        leftMessageView = holper.getView(R.id.friend_message);
        rightMessageView = holper.getView(R.id.user_message);

        User user = ApplicationData.getInstance().getUserInfo();
        timeView.setText(item.getSendTime());
        if (item.getMessageType() == ChatEntity.SEND) {
            leftLayout.setVisibility(View.GONE);
            rightLayout.setVisibility(View.VISIBLE);

            rightMessageView.setText(item.getContent());
        } else if (item.getMessageType() == ChatEntity.RECEIVE) {// 本身作为接收方
            leftLayout.setVisibility(View.VISIBLE);
            rightLayout.setVisibility(View.GONE);
//            Bitmap photo = ApplicationData.getInstance().getFriendPhotoMap()
//                    .get(item.getSenderId());
//            if (photo != null)
//                leftPhotoView.setImageBitmap(photo);
            leftMessageView.setText(item.getContent());
        }

    }
}