package com.pacia.ptms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;
import com.pacia.ptms.utils.ToolUtils;

import butterknife.BindView;


/**
 * Created by zheng on 2017/11/27.
 */

public class PhotoFragment extends BaseFragment {
    private static final String TAG = "PhotoFragment";
    @BindView(R.id.photoview)
    PhotoView mPhotoView;

    private String url;

    /**
     * 获取这个fragment需要展示图片的url
     *
     * @param url
     * @return
     */
    public static PhotoFragment newInstance(String url) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_img;
    }

    @Override
    public void initView() {
        url = getArguments().getString("url");
        setmPhotoView();
    }

    private void setmPhotoView() {
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER);
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        mPhotoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {

            }
        });
        ToolUtils.showImage(fContext, url, mPhotoView);

    }

    @Override
    public void initData() {

    }

    @Override
    public void lazyLoadData() {
//        ImageLoader.getInstance().displayImage(url, mPhotoView, options, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                dialogUtils.createLoadingDialog(fContext, "", true);
//                Log.e(TAG, "onLoadingStarted: ");
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                dialogUtils.dismissLoading();
//                Log.e(TAG, "onLoadingFailed: ");
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                dialogUtils.dismissLoading();
//                Log.e(TAG, "onLoadingComplete: ");
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                dialogUtils.dismissLoading();
//                Log.e(TAG, "onLoadingCancelled: ");
//            }
//        });
    }

}
