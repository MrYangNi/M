package com.project.yang.m.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.yang.m.R;
import com.project.yang.m.databinding.DrawerLayoutRecyclerViewItemBinding;

import java.util.List;

/**
 * Created by NiYang on 2017/3/28.
 */

public class  DrawerLayoutRecyclerViewAdapter extends RecyclerView.Adapter<DrawerLayoutRecyclerViewAdapter.MyHolder> {
    private static final String TAG = "DrawerLayoutRecyclerViewAdapter";
    private OnItemClickListener onItemClickListener = null;
    private Context mContext = null;
    private List<String> titles = null;
    private int[] img = null;

    public DrawerLayoutRecyclerViewAdapter(Context context,List<String> titles, int[] img) {
        this.titles = titles;
        this.img = img;
        this.mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DrawerLayoutRecyclerViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), R.layout.drawer_layout_recycler_view_item, parent, false);
        return new MyHolder(binding,this.onItemClickListener);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.binding.imgLabel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), img[position]));
        holder.binding.txtOption.setText(this.titles.get(position));
    }


    @Override
    public int getItemCount() {
        return this.titles.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private DrawerLayoutRecyclerViewItemBinding binding = null;
        private OnItemClickListener onItemClickListener = null;

        public MyHolder(DrawerLayoutRecyclerViewItemBinding itemBinding,OnItemClickListener onItemClickListener) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
            this.onItemClickListener = onItemClickListener;
            this.binding.getRoot().setOnClickListener(this);
            this.binding.getRoot().setBackgroundResource(R.drawable.item_bg);
        }

        @Override
        public void onClick(View view) {
            this.onItemClickListener.onItemClick(this.binding,getPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DrawerLayoutRecyclerViewItemBinding itemBinding, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

}
