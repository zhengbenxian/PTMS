package com.pacia.ptms.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.pacia.ptms.MyApplication;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.main.MainActivity;
import com.pacia.ptms.adapter.MainPagerAdapter;
import com.pacia.ptms.bean.CommonBean;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.widget.CustomDatePicker;
import com.pacia.ptms.widget.NoScrollViewPager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by p on 2018/03/15.
 */

public class ToolUtils {
    private static final String TAG = "ToolUtils";
    //权限RequestCode
    public static final int PERMISSION_CODE = 111;

    //MD5
    public static String getMD5Str(String pwd) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = pwd.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    //检查用户权限
    public static void checkPermission(Context context, String[] permissions) {
        List<String> addList = new ArrayList<>();
        for (String per : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.
                    checkSelfPermission(context, per)) {
                //没有授权
                addList.add(per);
            }
        }
        String[] temp = new String[addList.size()];
        if (addList.size() > 0) {
            ActivityCompat.requestPermissions((Activity) context, addList.toArray(temp),
                    PERMISSION_CODE);
        }
    }

    //登录失败返回错误码
    public static void getLoginError(int code) {
        switch (code) {
            case -1:
                ToastUtils.showShort("登录失败");
                break;
            case 3:
                ToastUtils.showShort("验证码为空");
                break;
            case 4:
                ToastUtils.showShort("角色不正确");
                break;
            case 5:
                ToastUtils.showShort("验证码错误");
                break;
            case 11:
                ToastUtils.showShort("登录名错误");
                break;
            case 12:
                ToastUtils.showShort("登录密码错误");
                break;
            case 21:
                ToastUtils.showShort("账户已被停用");
                break;
            case 31:
                ToastUtils.showShort("数据权限不正确");
                break;
            case 32:
                ToastUtils.showShort("功能权限不正确");
                break;
            case 41:
                ToastUtils.showShort("用户已经过登录验证，但获取其信息时发生异常，未能在我库中查询到其信息");
                break;
            case 120:
                ToastUtils.showShort("表示OA中有异常");
                break;
        }
    }

    /**
     * 是否开启定位
     *
     * @param context
     * @return
     */
    public static boolean isLocation(Context context) {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNet = locationManager.isProviderEnabled
                (LocationManager.NETWORK_PROVIDER);
        boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPS || isNet)
            return true;
        return false;
    }

    /**
     * 判断网络是否连接
     *
     * @param
     * @return
     */
    public static boolean isNetWork() {
        if (null != MyApplication.getInstance()) {
            ConnectivityManager manager = (ConnectivityManager)
                    MyApplication.getInstance().getSystemService
                            (Context.CONNECTIVITY_SERVICE);
            if (null == manager)
                return false;
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (null != info && info.isAvailable())
                return true;
        }
        return false;
    }

    /**
     * 设置Tab
     *
     * @param inflater
     * @param layout
     * @param title
     * @param imgs
     */
    public static void setMainTab(LayoutInflater inflater, TabLayout layout,
                                  int title[], int imgs[], NoScrollViewPager viewPager) {
        View view;
        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = layout.newTab();
            view = inflater.inflate(R.layout.tab_main_view, null);
            TextView tv = view.findViewById(R.id.tab_tv_title);
            tv.setText(title[i]);
            ImageView img = view.findViewById(R.id.tab_img_icon);
            if (null != imgs) {
                img.setImageResource(imgs[i]);
            } else {
                img.setVisibility(View.GONE);
            }
            tab.setCustomView(view);
            layout.addTab(tab);
        }
        layout.addOnTabSelectedListener
                (new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public static void setSubTab(LayoutInflater inflater, TabLayout layout,
                                 int title[], int imgs[], NoScrollViewPager viewPager) {
        View view;
        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = layout.newTab();
            view = inflater.inflate(R.layout.tab_sub_view, null);
            TextView tv = view.findViewById(R.id.tab_tv_title);
            tv.setText(title[i]);
            ImageView img = view.findViewById(R.id.tab_img_icon);
            if (null != imgs) {
                img.setImageResource(imgs[i]);
            } else {
                img.setVisibility(View.GONE);
            }
            tab.setCustomView(view);
            layout.addTab(tab);
        }
        layout.addOnTabSelectedListener
                (new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    /**
     * SwipeRefreshLayout
     *
     * @param context
     * @param layout
     */
    public static void createSwipeLayout(Context context, SwipeRefreshLayout layout) {
        layout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        layout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark);
        //设置下拉进度出现位置
        layout.setProgressViewOffset(true, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
                        context.getResources().getDisplayMetrics()));
        layout.setRefreshing(true);
    }

    /**
     * 设置recycleview manager
     *
     * @param context
     * @param view
     */
    public static void createRecycleManager(Context context, RecyclerView view) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(manager);
    }

    public static void createRecycleGridManager(Context context, RecyclerView view, int spanCount) {
        GridLayoutManager manager = new GridLayoutManager(context, spanCount);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(manager);
    }

    /**
     * 日期选择控件
     *
     * @param context
     * @param cdpStart
     * @param tv
     */
    public static void initDatePicker(Context context, CustomDatePicker cdpStart,
                                      final TextView tv, final boolean isSpecific, int index) {
        int year = DateUtils.getYear();
        //开始日期选择框
        cdpStart = new CustomDatePicker(context, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                if (!"".equals(time)) {
                    if (isSpecific) {
                        tv.setText(time);
                    } else {
                        tv.setText(time.split(" ")[0]);
                    }
                }
            }
        }, DateUtils.setDate(year - index, 1, 1),
                DateUtils.setDate(year + index, 12, 31));
        cdpStart.showSpecificTime(isSpecific); // 不显示时和分
        cdpStart.setIsLoop(false); // 不允许循环滚动
        final CustomDatePicker finalcdpStart = cdpStart;
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidDate(tv.getText().toString(), "yyyy-MM-dd") ||
                        isValidDate(tv.getText().toString(), "yyyy-MM-dd HH:mm")) {
                    finalcdpStart.show(tv.getText().toString());
                } else {
                    if (isSpecific)
                        finalcdpStart.show(DateUtils.getNowDate("yyyy-MM-dd HH:mm"));
                    else
                        finalcdpStart.show(DateUtils.getNowDate());
                }
            }
        });
    }

    public static void initDatePicker(Context context, CustomDatePicker picker,
                                      final TextView tv) {
        initDatePicker(context, picker, tv, false, 20);
    }

    /**
     * 判断字符串是否是一个合法的日期格式
     *
     * @param date
     * @param template
     * @return
     */
    public static boolean isValidDate(String date, String template) {
        boolean convertSuccess = true;
        // 指定日期格式
        SimpleDateFormat format = new SimpleDateFormat(template, Locale.CHINA);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2015/02/29会被接受，并转换成2015/03/01
            format.setLenient(false);
            format.parse(date);
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 打开相册
     *
     * @param context
     */
    public static void openPhotoAlbum(Activity context) {
        openPhotoAlbum(context, Constant.CHOOSE_PHOTO_CODE);
    }

    public static void openPhotoAlbum(Activity context, int code) {
        Intent intent_photo = new Intent();
        intent_photo.setAction(Intent.ACTION_GET_CONTENT);// 打开图库获取图片
        intent_photo.setAction(Intent.ACTION_PICK);// 打开图库获取图片
        intent_photo.setType("image/*");// 这个参数是确定要选择的内容为图片
        intent_photo.putExtra("return-data", true);
        intent_photo.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent_photo, code);
    }

    /**
     * 打开相机
     *
     * @param activity
     * @param file
     */
    public static void openCamera(Activity activity, File file, int code) {
        //获取系統版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (FileUtils.isSdCard()) {
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                Uri uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, code);
    }

    public static void openCamera(Activity activity, File file) {
        openCamera(activity, file, Constant.CHOOSE_CAMERA_CODE);
    }

    /**
     * 打开摄像机
     *
     * @param activity
     * @param file
     */
    public static void openVideo(Activity activity, File file) {
        if (null == file) {
            ToastUtils.showShort("拍摄出错");
            return;
        }
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 100 * 1024 * 1024L);
        intent.putExtra(MediaStore.EXTRA_FULL_SCREEN, true);
        activity.startActivityForResult(intent, Constant.CHOOSE_VIDEO_CODE);
    }

    public static void sendBroadcastIntent(Context ctx, String action,
                                           String key, String msg) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(key, msg);
        ctx.sendBroadcast(intent);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    //下载文件
    public static boolean downFile(Context context, File file, InputStream is) {
        boolean isSuccess = false;
        byte[] buffer = new byte[1024];
        int len;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            bis.close();
            is.close();
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    //关闭软键盘
    public static void closeSoft(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        View view = context.getWindow().peekDecorView();
        if (isOpen)
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //跳转拨号界面
    public static void openPhone(Activity activity, String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            activity.startActivity(intent);
        }
    }

    //dp 转px
    public static int dip2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    /**
     * px转换dp
     */
    public static int px2dip(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * px转换sp
     */
    public static int px2sp(Context context, int pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp转换px
     */
    public static int sp2px(Context context, int spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 手机号验证
     *
     * @param mobileNums
     * @return
     */
    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(19[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    //固定电话验证
    public static boolean isTel(String mobileNums) {
        String reg = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(reg);
    }

    //判断gps是否打开
    public static boolean openGPSSettings(Context context) {
        boolean isOpen = true;
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            isOpen = false;
        }
        return isOpen;
    }

    //glide 显示图片
    public static void showImage(Context context, Object url, ImageView view, boolean type) {
        showImage(context, url, view, false, type);
    }

    public static void showImage(Context context, Object url, ImageView view) {
        showImage(context, url, view, false, false);
    }

    @SuppressLint("CheckResult")
    public static void showImage(Context context, Object url, ImageView view,
                                 boolean isCircle, boolean type) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(5);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .error(R.drawable.ic_img_failed);
        if (isCircle) {
            options.circleCrop();
        } else {
            if (type) {
                options.transforms(new CenterInside(), roundedCorners);
            } else {
                options.transforms(new CenterCrop(), roundedCorners);
            }
        }
        Glide.with(context).load(url).apply(options).into(view);
    }

    //获得版本号
    public static String getVersionCode(Context context) {
        String version = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            int code = info.versionCode;
            String versionName = info.versionName;
            Log.e(TAG, "getVersionCode: " + code + "   -- " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    private static String id = "my_channel_04";
    private static String name = "我是渠道名字";

    /**
     * 创建通知
     *
     * @param context
     * @param msg
     */
    public static void createNotify(Context context, String msg) {
        Intent mIntent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, mIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        final NotificationManager manager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.progress_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name,
                    NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.enableLights(false);
            mChannel.enableVibration(false);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//设置在锁屏界面上显示这条通知
            Uri mUri = Settings.System.DEFAULT_NOTIFICATION_URI;
            if (null != mUri) {
//                mChannel.setSound(mUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
                mChannel.setSound(null, null);
            }
            manager.createNotificationChannel(mChannel);
            final Notification.Builder builder = new Notification.Builder(context, id)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(msg)
                    .setContentText(msg)
//                    .setOngoing(false)
//                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
            builder.setProgress(100, 0, false);
            manager.notify(1, builder.build());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i <= 100; i++) {
                        builder.setProgress(100, i, false);
                        //下载进度提示
                        builder.setContentText("下载" + i + "%");
                        manager.notify(1, builder.build());
                        try {
                            Thread.sleep(50);//演示休眠50毫秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    manager.cancel(1);
                }
            }).start();
        } else {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, id)
                            .setContentTitle(msg)
                            .setContentText(msg)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .setCustomContentView(views)
                            .setOngoing(false)
                            .setDefaults(Notification.DEFAULT_ALL);
            Notification notification = builder.build();
            manager.notify(2, notification);
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param context
     */
    public static void setStatusBarFullTransparent(Activity context) {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = context.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        if (null != context) {
            int resourceId = context.getResources().getIdentifier("status_bar_height",
                    "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 根据经纬度获取地址
     *
     * @param context
     * @param location
     * @return
     */
    public static String getAddress(Context context, Location location) {
        String city = "";
        try {
            List<Address> addresses = new Geocoder(context).getFromLocation(
                    location.getLatitude(), location.getLongitude(), 3);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                city = address.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

    /**
     * 初始化viewpager
     *
     * @param viewPager
     * @param tabLayout
     * @param manager
     * @param fragments
     */
    public static void initViewPager(NoScrollViewPager viewPager, TabLayout tabLayout,
                                     FragmentManager manager, Fragment[] fragments) {
        MainPagerAdapter adapter = new MainPagerAdapter(manager, fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener
                (new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(4);
    }

    public static String getPhotoPath(Activity context, Uri uri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.managedQuery
                    (uri, proj, null, null, null);
            int actual_image_column_index = cursor.getColumnIndexOrThrow
                    (MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String img_path = cursor.getString(actual_image_column_index);
            return img_path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 显示popu
     */
    private static PopupWindow popupWindow;

    public static ListView showPopupWin(Context context, final View tv, BaseAdapter adapter) {
        final View v = LayoutInflater.from(context).inflate
                (R.layout.layout_popupwindow, null);
        ListView listView = v.findViewById(R.id.listView_root);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        int width = context.getResources().getDisplayMetrics().widthPixels * 1 / 2;
        int height = context.getResources().getDisplayMetrics().heightPixels * 1 / 3;
        popupWindow = new PopupWindow(v, width, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(tv);
        return listView;
    }

    public static void dimissPopu() {
        if (null != popupWindow)
            popupWindow.dismiss();
    }

    //textview 设置 下划线
    public static void setTvUnderLine(final Activity activity, final TextView tv) {
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv.getPaint().setAntiAlias(true);//抗锯齿
        tv.setTextColor(activity.getResources().getColor(R.color.color_green));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolUtils.openPhone(activity, tv.getText().toString());
            }
        });
    }

    //获取调运状态
    public static List<CommonBean> getTransStatue() {
        List<CommonBean> list = new ArrayList<>();
        CommonBean cb1 = new CommonBean();
        cb1.setName("不限");
        cb1.setsGid("");
        CommonBean cb2 = new CommonBean();
        cb2.setName("未发班");
        cb2.setsGid("Z0");
        CommonBean cb3 = new CommonBean();
        cb3.setName("已发班");
        cb3.setsGid("00");
        CommonBean cb4 = new CommonBean();
        cb4.setName("已入库");
        cb4.setsGid("10");
        CommonBean cb5 = new CommonBean();
        cb5.setName("已出库");
        cb5.setsGid("20");
        CommonBean cb6 = new CommonBean();
        cb6.setName("确认卸油");
        cb6.setsGid("30");
        CommonBean cb7 = new CommonBean();
        cb7.setName("确认收油");
        cb7.setsGid("40");
        CommonBean cb8 = new CommonBean();
        cb8.setName("已完成");
        cb8.setsGid("50");
        CommonBean cb9 = new CommonBean();
        cb9.setName("已作废");
        cb9.setsGid("60");
        list.add(cb1);
        list.add(cb2);
        list.add(cb3);
        list.add(cb4);
        list.add(cb5);
        list.add(cb6);
        list.add(cb7);
        list.add(cb8);
        return list;
    }

    //获取等级
    public static List<CommonBean> getGradeList() {
        List<CommonBean> list = new ArrayList<>();
        CommonBean cb1 = new CommonBean();
        cb1.setName("全部");
        cb1.setsGid("");
        CommonBean cb2 = new CommonBean();
        cb2.setName("A级");
        cb2.setsGid("A");
        CommonBean cb3 = new CommonBean();
        cb3.setName("B级");
        cb3.setsGid("B");
        CommonBean cb4 = new CommonBean();
        cb4.setName("C级");
        cb4.setsGid("C");
        list.add(cb1);
        list.add(cb2);
        list.add(cb3);
        list.add(cb4);
        return list;
    }

    //获取状态
    public static List<CommonBean> getStatueList() {
        List<CommonBean> list = new ArrayList<>();
        CommonBean cb1 = new CommonBean();
        cb1.setName("全部");
        cb1.setsGid("");
        CommonBean cb2 = new CommonBean();
        cb2.setName("未处理");
        cb2.setsGid("00");
        CommonBean cb3 = new CommonBean();
        cb3.setName("已整改");
        cb3.setsGid("10");
        CommonBean cb4 = new CommonBean();
        cb4.setName("未合格");
        cb4.setsGid("20");
        CommonBean cb5 = new CommonBean();
        cb5.setName("已合格");
        cb5.setsGid("30");
        list.add(cb1);
        list.add(cb2);
        list.add(cb3);
        list.add(cb4);
        list.add(cb5);
        return list;
    }

    public static String stringFormat(Context context, int sId, Object... objects) {
        if (0 == sId || null == objects)
            return "";
        return String.format(context.getResources().getString(sId), objects);
    }
}
