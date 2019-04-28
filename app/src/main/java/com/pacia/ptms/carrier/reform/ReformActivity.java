package com.pacia.ptms.carrier.reform;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.PicAdapter;
import com.pacia.ptms.utils.DialogUtils;
import com.pacia.ptms.utils.FileUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.MyGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.pacia.ptms.service.Constant.CHOOSE_CAMERA_CODE;
import static com.pacia.ptms.service.Constant.CHOOSE_PHOTO_CODE;

/**
 * 承运商整改界面
 */
public class ReformActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.et_carrier_reform)
    EditText et_carrier_reform;
    @BindView(R.id.gv_carrier)
    MyGridView gv_carrier;
    private List<String> list_path = new ArrayList<>();
    private PicAdapter adapter;
    private DialogUtils dialogUtils;
    private File file;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reform;
    }

    @Override
    public void initView() {
        setTopTitle("违规整改");
        setRightMsg("提交");
        dialogUtils = new DialogUtils();
        adapter = new PicAdapter(this, list_path, true);
        gv_carrier.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gv_carrier.setOnItemClickListener(this);
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
        setRightClickListener(new OnRightClickListen() {
            @Override
            public void onRightClick(View view) {
                dialogUtils.createAlertDialog(ReformActivity.this, "提示", "确认提交", true);
                dialogUtils.setOnSureClickListener(new DialogUtils.onSureClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });
    }

    private void commit() {
        Map<String, RequestBody> map = new HashMap<>();
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        MultipartBody.Part[] parts = new MultipartBody.Part[list_path.size()];
        for (int i = 0; i < list_path.size(); i++) {
            if (list_path.get(i).startsWith("file:")) {
                File file = new File(list_path.get(i).substring(7));
                RequestBody bodyPath = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                parts[i] = MultipartBody.Part.createFormData("file", file.getName(), bodyPath);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getCount() - 1 == position) {
            dialogUtils.createPhotoDialog(ReformActivity.this);
            dialogUtils.setOnCameraClickListener(new DialogUtils.onCameraClickListener() {
                @Override
                public void onClick(View view) {
                    String cameraPath = FileUtils.createFile(ReformActivity.this,
                            "camera");
                    String imgName = System.currentTimeMillis() + ".png";
                    file = new File(cameraPath, imgName);
                    ToolUtils.openCamera(ReformActivity.this, file);
                }
            });
        }
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
}
