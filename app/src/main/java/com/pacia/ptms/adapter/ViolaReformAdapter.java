package com.pacia.ptms.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.ViolaReformBean;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.MyGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 整改处理
 * Created by p on 2019/03/28.
 */

public class ViolaReformAdapter extends BaseQuickAdapter<ViolaReformBean, BaseViewHolder> {
    private Context context;


    public ViolaReformAdapter(Context context, int layoutResId, @Nullable List<ViolaReformBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ViolaReformBean item) {
        helper.setText(R.id.tv_item_viola_time, DateUtils.timeStampToDate
                (item.getWgzgjlInsertDate()))
                .setText(R.id.tv_item_viola_content, item.getWgzgjlDescribe())
                .setText(R.id.tv_item_viola_name, item.getUserName())
                .setText(R.id.tv_item_viola_tel, item.getUserPhone());
        TextView tv = helper.getView(R.id.tv_item_viola_tel);
        MyGridView gv = helper.getView(R.id.gv_viola);
        ToolUtils.openPhone((Activity) context, tv.getText().toString());
        String imgUrls = item.getWgzgjlImgUrl();
        List<String> list_urls = new ArrayList<>();
        if (!"".equals(imgUrls)) {
            if (imgUrls.contains(",")) {
                String urls[] = imgUrls.split(",");
                list_urls = Arrays.asList(urls);
            } else {
                list_urls.add("http://s9.rr.itc.cn/r/wapChange/20174_9_12/a0dm3a7898206328542.jpg");
//                list_urls.add(R.mipmap.car+"");
//                list_urls.add(imgUrls);
            }
            PicAdapter adapter = new PicAdapter(context, list_urls, false);
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
