package com.pacia.ptms.oildepot.auditing;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.PicAdapter;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.DialogUtils;
import com.pacia.ptms.utils.FileUtils;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.SPUtils;
import com.pacia.ptms.utils.ToastUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.pacia.ptms.widget.MyGridView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

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
    private String gid = "", plate = "", content = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_viola_create;
    }

    @Override
    public void initView() {
        dialogUtils = new DialogUtils();
        gid = getIntent().getStringExtra("0");
        plate = getIntent().getStringExtra("1");
        setTopTitle("创建违规");
        setRightMsg("提交");
        tv_viola_doc_plate.setText(plate);
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
        setRightClickListener(new OnRightClickListen() {
            @Override
            public void onRightClick(View view) {
                content = et_viola_doc.getText().toString();
                if ("".equals(content)) {
                    ToastUtils.showShort("请填写违规描述");
                    return;
                }
                if (list_path.size() <= 0) {
                    ToastUtils.showShort("请至少选择一张图片");
                    return;
                }
                dialogUtils.createAlertDialog((Activity) context, "提示", "确认提交", true);
                dialogUtils.setOnSureClickListener(new DialogUtils.onSureClickListener() {
                    @Override
                    public void onClick(View view) {
                        commitCreateViola();
                    }
                });
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

    //提价违规单
    private void commitCreateViola() {
        Map<String, RequestBody> map = new HashMap<>();
        JSONObject jo = new JSONObject();
        try {
            jo.put("gid", gid);
            jo.put("carrierGid", SPUtils.getUserGid(context));
            jo.put("plateNo", plate);
            jo.put("grade", "A");
            jo.put("describe", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),
                jo.toString());
        map.put("orderRe", body);
        MultipartBody.Part[] parts = new MultipartBody.Part[list_path.size()];
        for (int i = 0; i < parts.length; i++) {
            if (list_path.get(i).startsWith("file://")) {
                File file = new File(list_path.get(i).substring(7));
                RequestBody path = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                parts[i] = MultipartBody.Part.createFormData("file", file.getName(), path);
            }
        }
        ServiceFactory.getService(ApiService.class)
                .createViola(map, parts)
                .compose(SchedulersHelper.<ResponseBody>io_main(context, true, "提交中..."))
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            ToastUtils.showShort("提交成功");
                            setRightVisibility(View.GONE);
                            et_viola_doc.setEnabled(false);
                        }

                    }

                    @Override
                    public void onError(String error) {
                        ToastUtils.showShort("提交失败" + error);
                    }
                });
    }
}
