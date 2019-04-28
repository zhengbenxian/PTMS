package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.SafeCheckBean;

import java.util.List;

/**
 * Created by p on 2019/03/28.
 */

public class SafeCheckAdapter extends BaseQuickAdapter<SafeCheckBean, BaseViewHolder> {
    private Context context;
    private SparseArray<String> choseArray;

    public SafeCheckAdapter(Context context, int layoutResId, @Nullable List<SafeCheckBean> data,
                            SparseArray<String> choseArray) {
        super(layoutResId, data);
        this.context = context;
        this.choseArray = choseArray;
    }

    @Override
    protected void convert(BaseViewHolder helper, SafeCheckBean item) {
        final int index = helper.getAdapterPosition();
        helper.setText(R.id.tv_safe_check_name, item.getJcgzName());
        RadioGroup rg = helper.getView(R.id.rg_safe_check);
        RadioButton rb_have = helper.getView(R.id.rb_safe_check_have);
        RadioButton rb_no = helper.getView(R.id.rb_safe_check_no);
        if (item.getJcgzHaveNo().equals("有")) {
            rb_have.setChecked(true);
        } else {
            rb_no.setChecked(true);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_safe_check_have:
                        choseArray.setValueAt(index, "有");
                        break;
                    case R.id.rb_safe_check_no:
                        choseArray.setValueAt(index, "无");
                        break;
                }
            }
        });
    }
}
