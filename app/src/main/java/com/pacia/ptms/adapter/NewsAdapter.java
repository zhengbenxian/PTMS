package com.pacia.ptms.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.NewsBean;

import java.util.List;

/**
 * Created by p on 2019/03/12.
 */

public class NewsAdapter extends BaseQuickAdapter<NewsBean, BaseViewHolder> {

    public NewsAdapter(int layoutResId, @Nullable List<NewsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {
        helper.setText(R.id.tv_news_title, item.getTitle())
                .setText(R.id.tv_news_content, item.getPithy());
    }
}
