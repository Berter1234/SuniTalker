package com.silver.chat.ui.contact.group;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silver.chat.MainActivity;
import com.silver.chat.R;
import com.silver.chat.base.BasePagerFragment;
import com.silver.chat.base.Common;
import com.silver.chat.network.SSIMGroupManger;
import com.silver.chat.network.SSIMLoginManger;
import com.silver.chat.network.callback.ResponseCallBack;
import com.silver.chat.network.requestbean.SetGroupManagerBody;
import com.silver.chat.network.responsebean.BaseResponse;
import com.silver.chat.ui.login.LoginActivity;
import com.silver.chat.util.AppManager;
import com.silver.chat.util.NetUtils;
import com.silver.chat.util.PreferenceUtil;
import com.silver.chat.util.ToastUtil;
import com.silver.chat.util.ToastUtils;
import com.silver.chat.view.dialog.TvLoginOutDialog;

import java.util.List;
import butterknife.BindView;
/**
 * Created by Joe on 2017/5/10.
 */


public class GroupLeftFragment extends BasePagerFragment {
    @BindView(R.id.grideview)
    GridView grideview;
    //单页最多条目个数
    public final int CHILD_NUM = 6;
    public List<String> mList;
    private GrideViewAdapter grideViewAdapter;

    public GroupLeftFragment(List list) {
        this.mList = list;
    }

    @Override
    protected void getData() {
        grideViewAdapter = new GrideViewAdapter();
        grideview.setAdapter(grideViewAdapter);


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_group_left;
    }

    class GrideViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return CHILD_NUM;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_grideview_group, null);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.tv_group_operat);
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(mList.get(position));
            //根据集合数据的不同展示不同图片
            switch (holder.textView.getText().toString()) {
                case "删除成员":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(DeleteGroupMemActivity.class);
                        }
                    });
                    break;
                case "群资料":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity,GroupDataActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
                case "群管理":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, GroupManagerActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
                case "解散群组":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDissolveDialog();
                        }
                    });
                    break;
                case "退出本群":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showQuitDialog();
                        }
                    });
                    break;
                case "消息免扰":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showExcuxeDialog();
                        }
                    });

                    break;
                case "置顶聊天":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtil.toastMessage(mActivity,"聊天已经置顶");
                        }
                    });

                    break;
                case "投诉":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mActivity, "已经投诉该群", Toast.LENGTH_SHORT).show();

                        }
                    });

                    break;
                case "清空记录":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mActivity, "记录已经清空", Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
            }


            return convertView;
        }
    }
    //解散群组的Dialog
    private void showDissolveDialog() {
        new TvLoginOutDialog(getActivity()).builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .setTitle("解散群组")
                .setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity, "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity,"确定",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
    //退出群组的Dialog
    private void showQuitDialog() {
        new TvLoginOutDialog(getActivity()).builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .setTitle("退出群组")
                .setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity, "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity, "确定", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
    //消息免扰的Dialog
    private void showExcuxeDialog() {
        new TvLoginOutDialog(getActivity()).builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .setTitle("消息免扰")
                .setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity, "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity, "确定", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
    static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }
}
