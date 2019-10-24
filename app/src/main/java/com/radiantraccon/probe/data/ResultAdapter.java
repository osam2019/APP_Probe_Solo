package com.radiantraccon.probe.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.radiantraccon.probe.R;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private ArrayList<ResultData> resultDataList;
    private OnItemClickListener listener;


    public ResultAdapter(ArrayList<ResultData> dataList) {
        this.resultDataList= dataList;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    public void setOnItemListener(ResultAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public ResultAdapter.ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_result_item, parent,false);

        return new ResultAdapter.ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultAdapter.ResultViewHolder holder, int position) {

        Glide.with(holder.itemView)
                .load(resultDataList.get(position).getImageUrl())
                .override(72, 72)
                .into(holder.icon);
        holder.bind(resultDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return resultDataList.size();
    }

    public ResultData getItem(int pos) {
        return resultDataList.get(pos);
    }

    public void addResultData(ResultData data) {
        resultDataList.add(data);
    }


    public class ResultViewHolder extends ViewHolder {
        ImageView icon;
        TextView title;
        TextView desc;
        TextView address;

        public ResultViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.imageView_icon);
            title = itemView.findViewById(R.id.textView_title);
            desc = itemView.findViewById(R.id.textView_desc);
            address = itemView.findViewById(R.id.textView_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(listener != null) {
                            listener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }

        public void bind(ResultData data) {
            // TODO: get image from URL
            //icon.setImageResource(data.getImageUrl());
            title.setText(data.getTitle());
            desc.setText(data.getDescription());
            address.setText(data.getAddress());
        }
    }
}
