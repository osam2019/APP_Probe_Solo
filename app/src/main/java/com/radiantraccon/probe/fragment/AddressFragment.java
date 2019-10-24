package com.radiantraccon.probe.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radiantraccon.probe.R;
import com.radiantraccon.probe.data.AddressAdapter;
import com.radiantraccon.probe.data.AddressData;
import com.radiantraccon.probe.data.AddressDataListWrapper;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {
    private String keywordText;
    private String descText;
    public AddressDataListWrapper addresses;
    public AddressFragment() {
        // Required empty public constructor
        addresses = new AddressDataListWrapper();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_address, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView_address);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        addresses.readAddressDataFile(getString(R.string.addressData_filename), getContext());

        Bundle bundle = getArguments();
        if(bundle != null) {
            Log.e("AddressFrag", keywordText + " " +descText);
            keywordText = bundle.getString("keywordText");
            descText = bundle.getString("descText");
        }

        addresses.initAdapter();
        final AddressAdapter adapter = addresses.getAddressAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                AddressData data = adapter.getItem(pos);
                Bundle bundle = new Bundle();
                bundle.putInt("imageId", data.getImageId());
                bundle.putString("title", data.getTitle());
                bundle.putString("address", data.getAddress());
                bundle.putString("keywordText", keywordText);
                bundle.putString("descText", descText);
                Navigation.findNavController(view).navigate(R.id.action_addressFragment_to_addFragment, bundle);
            }
        });

        return view;
    }
}
