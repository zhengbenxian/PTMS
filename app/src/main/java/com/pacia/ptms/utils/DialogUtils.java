package com.pacia.ptms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.pacia.ptms.R;
import com.pacia.ptms.service.Constant;

/**
 * 弹出框工具类
 * Created by p on 2018/04/19.
 */

public class DialogUtils {
    private static final String TAG = "DialogUtils";
    private static AlertDialog dialogLoding;
    private static AlertDialog dialogAlert;

    private onSureClickListener onSureClickListener;
    private onCancelClickListener onCancelClickListener;
    private onCameraClickListener onCameraClickListener;
    private onLoadingCancelListener onLoadingCancelListener;
    private Button btn_cancel;
    private Button btn_sure;

    /**
     * 默认 访问网络 加载dialog
     *
     * @param ctx
     * @param msg
     */
    public void createLoadingDialog(final Context ctx, String msg) {
        createLoadingDialog(ctx, msg, false);
    }

    public void createLoadingDialog(final Context ctx, String msg, boolean isCancel) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_default_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx, R.style.MyDialog_Style)
                .setView(view)
                .setCancelable(isCancel);
        TextView tv_def = view.findViewById(R.id.tv_dialog_def);
        tv_def.setText(msg);
        dialogLoding = builder.create();
        dialogLoding.setCancelable(isCancel);
        dialogLoding.setCanceledOnTouchOutside(false);
        dialogLoding.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (null != onLoadingCancelListener)
                    onLoadingCancelListener.cancel();
            }
        });
        dialogLoding.show();
    }

    /**
     * dialog dismiss
     */
    public void dismissLoading() {
        if (null != dialogLoding && dialogLoding.isShowing())
            dialogLoding.dismiss();
    }

    public interface onLoadingCancelListener {
        void cancel();
    }

    public void setOnLoadingCancelListener(onLoadingCancelListener onLoadingCancelListener) {
        this.onLoadingCancelListener = onLoadingCancelListener;
    }

    /**
     * 弹出dialog
     *
     * @param ctx
     */
    public void createAlertDialog(final Activity ctx, String title, String msg, boolean isCancel) {
        createAlertDialog(ctx, title, msg, isCancel, "确认", "取消");
    }

    public void createAlertDialog(final Activity ctx, String title, String msg, boolean isCancel,
                                  String sure, String cancel) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_layout, null);
        TextView tv_title = view.findViewById(R.id.dialog_title);
        TextView tv_msg = view.findViewById(R.id.dialog_content);
        btn_cancel = view.findViewById(R.id.dialog_btn_cancel);
        btn_cancel.setText(cancel);
        btn_sure = view.findViewById(R.id.dialog_btn_sure);
        btn_sure.setText(sure);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
                .setView(view)
                .setCancelable(isCancel);
        tv_title.setText(title);
        tv_msg.setText(msg);
        dialogAlert = builder.create();
        dialogAlert.setCanceledOnTouchOutside(isCancel);
        dialogAlert.show();
        final AlertDialog finalDialog = dialogAlert;
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog.dismiss();
                if (null != onSureClickListener)
                    onSureClickListener.onClick(view);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog.dismiss();
                if (null != onCancelClickListener)
                    onCancelClickListener.onClick(view);
            }
        });
    }

    public void createPhotoDialog(final Activity ctx) {
        createPhotoDialog(ctx, Constant.CHOOSE_PHOTO_CODE, false);
    }

    public void createPhotoDialog(final Activity ctx, final int code, final boolean isChoseMore) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_photo_alert, null);
        TextView tv_take_photo = view.findViewById(R.id.tv_take_photo);
        TextView tv_open_photo = view.findViewById(R.id.tv_open_photo);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
                .setView(view)
                .setCancelable(false);
        dialogAlert = builder.create();
        dialogAlert.setCanceledOnTouchOutside(true);
        dialogAlert.show();
        final AlertDialog finalDialog = dialogAlert;
        tv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog.dismiss();
                if (null != onCameraClickListener) {
                    onCameraClickListener.onClick(view);
                }
            }
        });
        tv_open_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog.dismiss();
                if (isChoseMore) {
                    ImageSelectorUtils.openPhoto(ctx, code);
                } else {
                    ToolUtils.openPhotoAlbum(ctx, code);
                }
            }
        });
    }

    public void setCancelVisibility(int visibility) {
        if (null != btn_cancel) {
            btn_cancel.setVisibility(visibility);
        }
    }

    //确定按钮接口
    public interface onSureClickListener {
        void onClick(View view);
    }

    public void setOnSureClickListener(onSureClickListener onSureClickListener) {
        this.onSureClickListener = onSureClickListener;
    }

    //取消按钮接口
    public interface onCancelClickListener {
        void onClick(View view);
    }

    public void setOnCancelClickListener(onCancelClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    //拍照
    public interface onCameraClickListener {
        void onClick(View view);
    }

    public void setOnCameraClickListener(onCameraClickListener onCameraClickListener) {
        this.onCameraClickListener = onCameraClickListener;
    }

    public boolean isShowDialog() {
        return dialogLoding.isShowing();
    }
}
