package com.silver.chat.ui.contact.group;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silver.chat.R;
import com.silver.chat.base.BasePagerFragment;
import com.silver.chat.util.ToastUtil;

import java.util.List;
import butterknife.BindView;
/**
 * Created by Joe on 2017/5/10.
 */

public class GroupRightFragment extends BasePagerFragment {
    @BindView(R.id.grideview)
    GridView grideview;
    //单页最多条目个数
    public final int CHILD_NUM = 6;
    public List<String> mList;
    private GrideViewAdapter grideViewAdapter;
    private AlertDialog.Builder builder;

    public GroupRightFragment(List list) {
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
            return mList.size() % CHILD_NUM;
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
            holder.textView.setText(mList.get(CHILD_NUM+position));
            //根据集合数据的不同展示不同图片
            switch (holder.textView.getText().toString()) {

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
                            Toast.makeText(mActivity, "聊天已经置顶", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case "投诉":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mActivity, "投诉成功", Toast.LENGTH_SHORT).show();

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
                case "更多":
                    holder.imageView.setImageResource(R.drawable.ic_stick_white);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mActivity, "我是更多", Toast.LENGTH_SHORT).show();


                        }
                    });
                    break;
            }
            return convertView;
        }
    }
    //消息免扰的Dialog
    private void showExcuxeDialog() {
        builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("确定退消息免扰");
        //builder.setMessage(R.string.dialog_message);

        //监听下方button点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(mActivity,"确定",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(mActivity, "取消", Toast.LENGTH_SHORT).show();
            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog= builder.create();
        dialog.show();
    }
    static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }

}