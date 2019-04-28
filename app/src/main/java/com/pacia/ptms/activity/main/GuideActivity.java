package com.pacia.ptms.activity.main;

import android.view.View;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.activity.login.LoginActivity;
import com.pacia.ptms.adapter.BannerImageLoader;
import com.pacia.ptms.utils.SPUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 引导页面
 */
public class GuideActivity extends BaseActivity {
    private static final String TAG = "GuideActivity";
    @BindView(R.id.banner_guide)
    Banner banner_guide;
    //标题
    private List<String> titles = new ArrayList<>();
    //图片路径
    private List<Integer> paths = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        setToolBarVisibility(View.GONE);
        titles.add("1");
        titles.add("2");
        paths.add(R.mipmap.img1);
        paths.add(R.mipmap.img2);
        banner_guide.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImages(paths)
                .setBannerTitles(titles)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();

        banner_guide.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position == paths.size() - 1) {
                    SPUtils.put(context, "isFirst", false);
                    doActivity(LoginActivity.class);
                    finish();
                }
            }
        });
    }

    @Override
    public void initData() {

    }
}
