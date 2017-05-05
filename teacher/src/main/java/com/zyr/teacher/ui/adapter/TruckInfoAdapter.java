package com.zyr.teacher.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xk on 2016/6/4 21:58.
 */
public class TruckInfoAdapter extends RecyclerView.Adapter<TruckInfoAdapter.MViewHolder> {
    private Context context;
    private List data = new ArrayList<>();
    private RecyclerView recyclerView;

    public TruckInfoAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = View.inflate(context, R.layout.item_truck_info, null);
        View v = null;
        return new MViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final MViewHolder holder, final int position) {

//        holder.root.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        holder.ic_call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MViewHolder extends RecyclerView.ViewHolder {

        public MViewHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * 设置数据，会清空以前数据
     *
     * @param datas
     */
    public void setData(List datas) {
        data.clear();
        if (null != datas) {
            data.addAll(datas);
        }
        notifyDataSetChanged();
        if (datas.size() == 0) {
            if (onDataEmptyListener != null) {
                onDataEmptyListener.onDataEmpty();
            }
        } else {
            if (onDataEmptyListener != null) {
                onDataEmptyListener.onDataNotEmpty();
            }
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnDataEmptyListener onDataEmptyListener;

    public interface OnDataEmptyListener {
        void onDataEmpty();

        void onDataNotEmpty();
    }

    public void setOnDataEmptyListener(OnDataEmptyListener onDataEmptyListener) {
        this.onDataEmptyListener = onDataEmptyListener;
    }


}

