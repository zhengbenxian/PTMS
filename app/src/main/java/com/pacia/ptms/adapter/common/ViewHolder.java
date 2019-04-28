package com.pacia.ptms.adapter.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private int mPosition;

    public ViewHolder(Context context, ViewGroup parent, int layoutid,
                      int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutid, parent,
                false);
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */

    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 通过控件的ID获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);

        }
        return (T) view;
    }

    public CheckBox getCheckBox(int viewId) {
        CheckBox view = (CheckBox) mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);

        }
        return view;
    }

    public View getConvertView() {
        return mConvertView;

    }

    public int getPosition() {
        return mPosition;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 为TextView设置颜色
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setColor(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setTextColor(resId);
        return this;
    }

    /**
     * 为TextView设置大小
     *
     * @param viewId
     * @param resId
     * @returnpublic Activity mActivity;
     */
    public ViewHolder setSize(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setTextSize(resId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setTvCompoundDrawables(int viewId, int leftId, int topId, int rightId,
                                             int bottomId) {
        TextView view = getView(viewId);
        view.setCompoundDrawablesWithIntrinsicBounds(leftId, topId, rightId, bottomId);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean isChecked) {
        CheckBox view = getView(viewId);
        view.setChecked(isChecked);
        return this;
    }

    public ViewHolder setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 为ImageView 设置图片
     *
     * @param viewId
     * @param bitmap
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;

    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageURL(int viewId, int resId) {
        ImageView view = getView(viewId);
        //ImageLoader.load(view,url);
        return this;
    }
}