package com.pacia.ptms.activity;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.PhotoPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class ImageActivity extends BaseActivity {
    private static final String TAG = "ImageActivity";

    @BindView(R.id.photoViewPager)
    ViewPager photoViewPager;
    @BindView(R.id.tv_num)
    TextView tvNum;

    private ArrayList<String> urlList;
    private PhotoPagerAdapter viewPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    public void initView() {
        setCanScroll(false);
        setTopTitle("查看图片");
        urlList = (ArrayList<String>) getIntent().getSerializableExtra("urls");
        init();
    }

    @Override
    public void initData() {

    }

    private void init() {
        viewPagerAdapter = new PhotoPagerAdapter(getSupportFragmentManager(), urlList);
        photoViewPager.setAdapter(viewPagerAdapter);
        photoViewPager.setOffscreenPageLimit(2);
        photoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvNum.setText(String.valueOf(position + 1) + "/" + urlList.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
