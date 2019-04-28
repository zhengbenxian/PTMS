package com.pacia.ptms.oildepot.auditing;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.PicAdapter;
import com.pacia.ptms.utils.DialogUtils;
import com.pacia.ptms.utils.FileUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.MyGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.pacia.ptms.service.Constant.CHOOSE_CAMERA_CODE;
import static com.pacia.ptms.service.Constant.CHOOSE_PHOTO_CODE;

/**
 * 违规单据提交
 */
public class CreateViolaActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "CreateViolaActivity";
    @BindView(R.id.tv_viola_doc_plate)
    TextView tv_viola_doc_plate;
    @BindView(R.id.tv_viola_doc_grade)
    TextView tv_viola_doc_grade;
    @BindView(R.id.et_viola_doc)
    TextView et_viola_doc;
    @BindView(R.id.gv_chose_img)
    MyGridView gv_chose_img;
    private List<String> list_path = new ArrayList<>();
    private PicAdapter adapter;
    private DialogUtils dialogUtils;
    private File file;
    private String gid = "", plate = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_viola_create;
    }

    @Override
    public void initView() {
        dialogUtils = new DialogUtils();
        setTopTitle("创建违规");
        setRightMsg("提交");
        adapter = new PicAdapter(this, list_path, true);
        gv_chose_img.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gv_chose_img.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        adapter.setOnErrorClickListener(new PicAdapter.onErrorClickListener() {
            @Override
            public void onErrorClick(View v, int position) {
                list_path.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSE_PHOTO_CODE) {
                Uri uri = data.getData();
                if (null != uri) {
                    String path = ToolUtils.getPhotoPath(this, uri);
                    list_path.add("file://" + path);
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == CHOOSE_CAMERA_CODE) {
                if (null != file) {
                    list_path.add("file://" + file.getAbsolutePath());
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getCount() - 1 == position) {
            dialogUtils.createPhotoDialog(CreateViolaActivity.this);
            dialogUtils.setOnCameraClickListener(new DialogUtils.onCameraClickListener() {
                @Override
                public void onClick(View view) {
                    String cameraPath = FileUtils.createFile(CreateViolaActivity.this,
                            "camera");
                    String imgName = System.currentTimeMillis() + ".png";
                    file = new File(cameraPath, imgName);
                    ToolUtils.openCamera(CreateViolaActivity.this, file);
                }
            });
        }
    }
}
