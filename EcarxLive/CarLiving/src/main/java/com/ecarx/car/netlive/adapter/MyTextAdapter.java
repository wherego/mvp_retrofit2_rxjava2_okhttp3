package com.ecarx.car.netlive.adapter;

import android.content.Context;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.base.BaseRecylerAdapter;
import com.ecarx.car.netlive.base.MyRecylerViewHolder;

import java.util.List;

/**
 * Created by anzhuo002 on 2016/7/5.
 */

public class MyTextAdapter extends BaseRecylerAdapter<String>{
    public MyTextAdapter(Context context, List<String> mDatas) {
        super(context, mDatas, R.layout.item_text);
    }

    /**
     * 这个方法 设置数据到item 中
     * @param holder
     * @param position
     */
    @Override
    public void convert(MyRecylerViewHolder holder, int position) {
        holder.setText(R.id.tvName, "youxin***" + position);
    }
}
