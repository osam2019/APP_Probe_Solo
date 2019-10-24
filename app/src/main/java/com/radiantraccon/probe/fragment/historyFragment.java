package com.radiantraccon.probe.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radiantraccon.probe.MainActivity;
import com.radiantraccon.probe.R;
import com.radiantraccon.probe.data.ResultAdapter;
import com.radiantraccon.probe.data.ResultData;
import com.radiantraccon.probe.data.ResultDataListWrapper;

/**
 * A simple {@link Fragment} subclass.
 */
public class historyFragment extends Fragment {
    ResultDataListWrapper histories = new ResultDataListWrapper();

    public historyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        histories.setResultDataList(((MainActivity)getActivity()).getHistoryList());
        initRecylcerView(view);
        return view;
    }

    private void initRecylcerView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView_history);
        final LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        histories.initResultAdapter();
        final ResultAdapter resultAdapter = histories.getResultAdapter();
        recyclerView.setAdapter(resultAdapter);

        resultAdapter.setOnItemListener(new ResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                // TODO: Move to webpage of clicked item
                ResultData data = resultAdapter.getItem(pos);
                Bundle bundle = new Bundle();
                bundle.putString("URL", data.getAddress());
                Navigation.findNavController(v).navigate(R.id.action_historyFragment_to_webFragment, bundle);

            }
        });
    }

}
