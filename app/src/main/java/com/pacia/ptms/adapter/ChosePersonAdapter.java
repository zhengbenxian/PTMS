package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.PersonBean;

import java.util.List;

/**
 * Created by p on 2019/04/29.
 */

public class ChosePersonAdapter extends BaseQuickAdapter<PersonBean, BaseViewHolder> {
    private Context context;

    public ChosePersonAdapter(Context context, int layoutResId, @Nullable List<PersonBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item) {
        helper.setText(R.id.tv_chose_person_name, item.getName())
                .setText(R.id.tv_chose_person_tel, item.getmPhone());
//        TextView tv = helper.getView(R.id.tv_chose_person_tel);
//        ToolUtils.openPhone((Activity) context, tv.getText().toString());
    }
}
