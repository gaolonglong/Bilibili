package com.don.bilibili.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.don.bilibili.utils.EmptyUtil;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<T> mList;

    public BaseAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemCount() {
        return EmptyUtil.isEmpty(mList) ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public abstract ViewHolder getViewHolder(ViewGroup parent, int viewType);
}