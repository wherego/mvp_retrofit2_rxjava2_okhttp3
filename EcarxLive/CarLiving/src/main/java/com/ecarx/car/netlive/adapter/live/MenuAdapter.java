package com.ecarx.car.netlive.adapter.live;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecarx.car.netlive.R;
import com.ecarx.car.netlive.listener.OnItemClickListener;
import com.ecarx.car.netlive.utils.SharedPreferencesUtils;
import com.ecarx.car.netlive.utils.SystemTool;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YoKeyword on 16/2/10.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mItems = new ArrayList<>();

    private SparseBooleanArray mBooleanArray;

    private OnItemClickListener mClickListener;

    private int mLastCheckedPosition = -1;

    public MenuAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setDatas(List<String> items) {
        mItems.clear();
        mItems.addAll(items);

        mBooleanArray = new SparseBooleanArray(mItems.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_menu, parent, false);
        final MyViewHolder holder = new MyViewHolder(view, getItemCount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (!mBooleanArray.get(position)) {
            holder.viewLine.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundResource(R.color.bg_app);
            holder.tvName.setTextColor(Color.BLACK);
        } else {
            holder.viewLine.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        }
        holder.tvName.setText(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItemChecked(int position) {
        mBooleanArray.put(position, true);

        if (mLastCheckedPosition > -1) {
            mBooleanArray.put(mLastCheckedPosition, false);
            notifyItemChanged(mLastCheckedPosition);
        }
        notifyDataSetChanged();

        mLastCheckedPosition = position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlMenu;
        View viewLine;
        TextView tvName;

        public MyViewHolder(View itemView, int itemCount) {
            super(itemView);
            viewLine = itemView.findViewById(R.id.view_line);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            rlMenu = (RelativeLayout) itemView.findViewById(R.id.rl_menu);

            //获取item高度逻辑    RecoverView的高度、屏幕高度-状态栏高度    / itemCount
            int sharedRecoverHeight = SharedPreferencesUtils.getInstance(mContext).getInt(SharedPreferencesUtils.recover_height);
            int sharedStatusHeight = SharedPreferencesUtils.getInstance(mContext).getInt(SharedPreferencesUtils.status_height);
            int recoverHeight = sharedRecoverHeight > 0 ?
                    sharedRecoverHeight :
                    (SystemTool.getScreenHeight(mContext) - (
                            sharedStatusHeight > 0 ? sharedStatusHeight : SystemTool.dip2px(mContext, 24)));

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, recoverHeight / itemCount);
            rlMenu.setLayoutParams(params);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
