package com.pacia.ptms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.bean.PersonBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 施工人员和安全监护 GridView adapter
 * Created by p on 2018/06/20.
 */

public class PersonHeadAdapter extends BaseAdapter {
    private static final String TAG = "PersonHeadAdapter";
    private Context context;
    private List<PersonBean> list;
    LayoutInflater layoutInflater;
    private boolean visibility;
    private int size = 0;

    public PersonHeadAdapter(Context context, List<PersonBean> list,
                             boolean visibility, int size) {
        this.context = context;
        this.list = list;
        this.visibility = visibility;
        layoutInflater = LayoutInflater.from(context);
        this.size = size;
    }

    @Override
    public int getCount() {
        if (size > 0) {
            if (visibility) {
                if (size <= list.size()) {
                    return size + 1;
                } else {
                    return list.size() + 1;
                }
            } else {
                if (size <= list.size()) {
                    return size;
                } else {
                    return list.size();
                }
            }
        } else {
            if (visibility)
                return list.size() + 1;//注意此处
            else
                return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PersonHeadAdapter.ViewHolder vh;
        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.layout_chose_person_head, null);
            vh = new PersonHeadAdapter.ViewHolder(convertView);
            convertView.setTag(vh);
        } else
            vh = (PersonHeadAdapter.ViewHolder) convertView.getTag();
        if (visibility) {
            if (position < list.size()) {
                vh.img_person_del.setVisibility(View.VISIBLE);
                PersonBean pb = list.get(position);
//                if (null != pb.getFileList()) {
//                    if (pb.getFileList().size() > 0) {
//                        PersonBean.FileListBean fb = pb.getFileList().get(0);
//                        if (pb.getSi_type() == 0) {
//                            ImageLoader.getInstance().displayImage(BaseUrl.BASE_URL + "file/" + fb.getThumbURL(),
//                                    vh.img_person_header, option());
//                        } else {
//                            vh.img_person_header.setColorFilter(getColorFilter());
//                            ImageLoader.getInstance().displayImage(BaseUrl.BASE_URL + "file/" + fb.getThumbURL(),
//                                    vh.img_person_header, option());
//                        }
//                    } else {
//                        if (pb.getSi_type() == 0) {
//                            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.ic_no_header,
//                                    vh.img_person_header, option());
//                        } else {
//                            vh.img_person_header.setColorFilter(getColorFilter());
//                            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.ic_no_header,
//                                    vh.img_person_header, option());
//                        }
//                    }
//                } else {
//                    if (pb.getSi_type() == 0) {
//                        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.ic_no_header,
//                                vh.img_person_header, option());
//                    } else {
//                        vh.img_person_header.setColorFilter(getColorFilter());
//                        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.ic_no_header,
//                                vh.img_person_header, option());
//                    }
//                }
                vh.tv_person_name.setText(pb.getName());
                vh.img_person_header.setImageResource(R.drawable.ic_no_header);
            } else {
                vh.img_person_header.setImageResource(R.drawable.ic_add);//最后一个显示加号图片
                vh.tv_person_name.setText("");
                vh.img_person_del.setVisibility(View.GONE);
                vh.img_person_del.setEnabled(false);
            }
        } else {
            PersonBean pb = list.get(position);
//                if (null != pb.getFileList()) {
//                    if (pb.getFileList().size() > 0) {
//                        PersonBean.FileListBean fb = pb.getFileList().get(0);
//                        if (pb.getSi_type() == 0) {
//                            ImageLoader.getInstance().displayImage(BaseUrl.BASE_URL + "file/" + fb.getThumbURL(),
//                                    vh.img_person_header, option());
//                        } else {
//                            vh.img_person_header.setColorFilter(getColorFilter());
//                            ImageLoader.getInstance().displayImage(BaseUrl.BASE_URL + "file/" + fb.getThumbURL(),
//                                    vh.img_person_header, option());
//                        }
//                    } else {
//                        if (pb.getSi_type() == 0) {
//                            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.ic_no_header,
//                                    vh.img_person_header, option());
//                        } else {
//                            vh.img_person_header.setColorFilter(getColorFilter());
//                            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.ic_no_header,
//                                    vh.img_person_header, option());
//                        }
//            } else {
//                    if (pb.getSi_type() == 0) {
//                        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.ic_no_header,
//                                vh.img_person_header, option());
//                    } else {
//                        vh.img_person_header.setColorFilter(getColorFilter());
//                        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.ic_no_header,
//                                vh.img_person_header, option());
//                    }
//            }
            vh.img_person_del.setVisibility(View.GONE);
            vh.tv_person_name.setText(pb.getName());
            vh.img_person_header.setImageResource(R.drawable.ic_no_header);
        }
        vh.img_person_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onErrorClickListener)
                    onErrorClickListener.onErrorClick(v, position);
            }
        });
        return convertView;
    }

    public void setListSize(int size) {

    }

    private onErrorClickListener onErrorClickListener;

    public void setOnErrorClickListener(onErrorClickListener onErrorClickListener) {
        this.onErrorClickListener = onErrorClickListener;
    }

    public interface onErrorClickListener {
        void onErrorClick(View v, int position);
    }

    class ViewHolder {
        @BindView(R.id.img_person_header)
        ImageView img_person_header;
        @BindView(R.id.tv_person_name)
        TextView tv_person_name;
        @BindView(R.id.img_person_del)
        ImageView img_person_del;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
