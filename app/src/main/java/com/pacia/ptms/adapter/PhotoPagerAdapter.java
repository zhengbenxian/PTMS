package com.pacia.ptms.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.pacia.ptms.activity.PhotoFragment;

import java.util.ArrayList;

/**
 * Created by zheng on 2017/11/27.
 */

public class PhotoPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<String> urlList;

    public PhotoPagerAdapter(FragmentManager fm, ArrayList<String> urlList) {
        super(fm);
        this.urlList=urlList;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(urlList.get(position));
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
