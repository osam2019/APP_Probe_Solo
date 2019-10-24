package com.radiantraccon.probe.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.radiantraccon.probe.R;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    // data list in RecyclerView
    private ArrayList<AddressData> addressDataList;
    private AddressAdapter.OnItemClickListener listener = null;
    // constructor
    public AddressAdapter(ArrayList<AddressData> dataList) {
        this.addressDataList = dataList;
    }
    // interface for onClicklistener
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }
    public void setOnItemListener(AddressAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public AddressAdapter.AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_address_item, parent,false);

        return new AddressAdapter.AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.AddressViewHolder holder, int position) {
        holder.bind(addressDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return addressDataList.size();
    }

    public void addAddressData(AddressData data) {
        addressDataList.add(data);
    }

    public AddressData getItem(int pos) {
        return addressDataList.get(pos);
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        TextView address;
        public AddressViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.imageView_icon);
            title = itemView.findViewById(R.id.textView_title);
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

        public void bind(AddressData data) {
            //icon.setImageResource(data.getImageId());
            title.setText(data.getTitle());
            address.setText(data.getAddress());
        }
    }
}
