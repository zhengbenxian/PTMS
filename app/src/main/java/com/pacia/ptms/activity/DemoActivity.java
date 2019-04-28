package com.pacia.ptms.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pacia.ptms.R;
import com.pacia.ptms.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoActivity extends AppCompatActivity {
    @BindView(R.id.toolBar1)
    Toolbar toolBar;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;

    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);
        ButterKnife.bind(this);
        toolBar.setTitle("哈哈哈哈哈哈哈哈哈哈");
        toolBar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_add_img));
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        init();
    }

    private void init(){
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout, toolBar,
                R.string.about, R.string.back) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawerlayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_normal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.action_search) {
            ToastUtils.showShort("111");
        } else if (menuItemId == R.id.action_notification) {
            ToastUtils.showShort("112");

        } else if (menuItemId == R.id.item1) {
            ToastUtils.showShort("1113");

        } else if (menuItemId == R.id.item2) {
            ToastUtils.showShort("1114");

        }
        return true;
    }
}
