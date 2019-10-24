package com.radiantraccon.probe.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radiantraccon.probe.MainActivity;
import com.radiantraccon.probe.R;
import com.radiantraccon.probe.data.ResultAdapter;
import com.radiantraccon.probe.data.ResultData;
import com.radiantraccon.probe.data.ResultDataListWrapper;

import java.util.ArrayList;

public class ResultFragment extends Fragment {
    private ResultDataListWrapper results;
    private String paramAddress;
    private String paramKeyword;
    private String paramStartPage;
    private String paramLastPage;


    public ResultFragment() {
        results = new ResultDataListWrapper();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        final TextView guideTextView = view.findViewById(R.id.textView_guide);
        final Button crawlButton = view.findViewById(R.id.button_crawl);
        final EditText editTextStartPage = view.findViewById(R.id.editText_startPage);
        final EditText editTextLastPage = view.findViewById(R.id.editText_lastPage);

        Bundle bundle = getArguments();
        if(bundle != null) {
            ArrayList<ResultData> list = bundle.getParcelableArrayList("results");
            paramAddress = bundle.getString("address");
            paramKeyword = bundle.getString("keyword");
            paramStartPage = bundle.getString("startPage");
            paramLastPage = bundle.getString("lastPage");

            if(list.size() != 0) {
                guideTextView.setText(paramStartPage + "페이지부터 " + paramLastPage + "페이지까지의 결과: ");
            } else {
                guideTextView.setText("결과가 없습니다!");
            }
            results.setResultDataList(list);
        }

        editTextStartPage.setText(paramStartPage);
        editTextLastPage.setText(paramLastPage);

        editTextLastPage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch(actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        crawlButton.performClick();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        crawlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).crawl(paramAddress, paramKeyword, editTextStartPage.getText().toString(), editTextLastPage.getText().toString(), "false");
            }
        });

        initRecylcerView(view);
        return view;
    }

    private void initRecylcerView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView_result);
        final LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        results.initResultAdapter();
        final ResultAdapter resultAdapter = results.getResultAdapter();
        recyclerView.setAdapter(resultAdapter);

        resultAdapter.setOnItemListener(new ResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                // TODO: Move to webpage of clicked item
                ResultData data = resultAdapter.getItem(pos);
                Bundle bundle = new Bundle();
                bundle.putString("URL", data.getAddress());
                ((MainActivity)getActivity()).addHistory(data);
                Navigation.findNavController(v).navigate(R.id.action_resultFragment_to_webFragment, bundle);

            }
        });
    }
}
