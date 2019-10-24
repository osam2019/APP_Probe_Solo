package com.radiantraccon.probe.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.radiantraccon.probe.R;

import java.util.ArrayList;

public class KeywordAdapter extends RecyclerView.Adapter<KeywordAdapter.KeywordViewHolder> {
    // data list in RecyclerView
    private ArrayList<KeywordData> keywordDataList;
    private OnItemClickListener listener = null;

    public KeywordAdapter(ArrayList<KeywordData> dataList) {
        this.keywordDataList = dataList;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    public void setOnItemListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public KeywordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_keyword_item, parent,false);

        return new KeywordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KeywordViewHolder holder, int position) {
        holder.bind(keywordDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return keywordDataList.size();
    }

    public KeywordData getItem(int pos) {
        return keywordDataList.get(pos);
    }

    public void addKeywordData(KeywordData data) {
        keywordDataList.add(data);
    }

    // keyword view holder class
    public class KeywordViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView keyword;
        TextView desc;
        TextView address;
        public KeywordViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.imageView_icon);
            keyword = itemView.findViewById(R.id.textView_keyword);
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

        public void bind(KeywordData data) {
            //icon.setImageResource(data.getImageId());
            keyword.setText(data.getKeyword());
            desc.setText(data.getDescription());
            address.setText(data.getAddress());
        }
    }
}
