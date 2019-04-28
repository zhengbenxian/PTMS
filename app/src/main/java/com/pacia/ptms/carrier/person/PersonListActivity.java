package com.pacia.ptms.carrier.person;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.PersonHeadAdapter;
import com.pacia.ptms.bean.PersonBean;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//人员全部列表
public class PersonListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.gv_driver_type)
    MyGridView gv_driver_type;
    @BindView(R.id.tv_driver_type)
    TextView tv_driver_type;
    private String type = "", title = "";
    private PersonHeadAdapter adapter;
    private List<PersonBean> list_pb = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_list;
    }

    @Override
    public void initView() {
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        list_pb = (List<PersonBean>) getIntent().getSerializableExtra("list");
        setTopTitle(title);
        if (type.equals(Constant.PERSON_DRIVER)) {
            tv_driver_type.setText("驾驶员");
        } else if (type.equals(Constant.PERSON_D_ESCORT)) {
            tv_driver_type.setText("押运员");
        }

        adapter = new PersonHeadAdapter(context, list_pb, false, 0);
        gv_driver_type.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gv_driver_type.setOnItemClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PersonBean pb = (PersonBean) parent.getItemAtPosition(position);
        doActivity(PersonInfoActivity.class, pb.getGid());
    }
}
