package com.pacia.ptms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pacia.ptms.R;
import com.pacia.ptms.utils.ToolUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片 显示 adapter
 * Created by p on 2019/03/12.
 */

public class PicAdapter extends BaseAdapter {
    private static final String TAG = "PicAdapter";
    private Context context;
    private List<String> list;
    LayoutInflater layoutInflater;
    //是否显示加号 和 删除
    private boolean visiAdd;

    public PicAdapter(Context context, List<String> list, boolean visiAdd) {
        this.context = context;
        this.list = list;
        this.visiAdd = visiAdd;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() + 1;//注意此处
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.layout_chose_img, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (visiAdd) {
            if (position < list.size()) {
                vh.img_del.setVisibility(View.VISIBLE);
                ToolUtils.showImage(context, list.get(position), vh.img_pic);
            } else {
                //最后一个显示加号图片
                ToolUtils.showImage(context, R.drawable.ic_add, vh.img_pic, true);
                vh.img_del.setVisibility(View.GONE);
            }
        } else {
            if (position < list.size()) {
                vh.img_del.setVisibility(View.GONE);
                ToolUtils.showImage(context, list.get(position), vh.img_pic);
            } else {
                vh.layout_gv_pic.setVisibility(View.GONE);
            }
        }
        vh.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onErrorClickListener)
                    onErrorClickListener.onErrorClick(v, position);
            }
        });
        return convertView;
    }

    public void setViewAddGone() {
        visiAdd = false;
        notifyDataSetChanged();
    }

    private onErrorClickListener onErrorClickListener;

    public void setOnErrorClickListener(onErrorClickListener onErrorClickListener) {
        this.onErrorClickListener = onErrorClickListener;
    }

    public interface onErrorClickListener {
        void onErrorClick(View v, int position);
    }

    class ViewHolder {
        @BindView(R.id.img_pic)
        ImageView img_pic;
        @BindView(R.id.layout_gv_pic)
        RelativeLayout layout_gv_pic;
        @BindView(R.id.img_del)
        ImageView img_del;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
