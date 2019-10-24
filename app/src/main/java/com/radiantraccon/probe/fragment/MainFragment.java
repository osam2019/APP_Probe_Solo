package com.radiantraccon.probe.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.radiantraccon.probe.CrawlOption;
import com.radiantraccon.probe.MainActivity;
import com.radiantraccon.probe.R;
import com.radiantraccon.probe.data.KeywordAdapter;
import com.radiantraccon.probe.data.KeywordData;
import com.radiantraccon.probe.data.KeywordDataListWrapper;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private int paramImageId;
    private String paramKeyword;
    private String paramAddress;
    private String paramDesc;
    private int sortMode = 0;
    // ex:) class Data;
    //      class KeywordData extends Data ... etc
    // Data ArrayList of RecyclerView
    public KeywordDataListWrapper keywords = new KeywordDataListWrapper();
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        sortMode = 0;
        keywords.readKeywordDataFile(getString(R.string.keywordData_filename), getContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            paramImageId = bundle.getInt("imageId");
            paramKeyword = bundle.getString("keyword");
            paramAddress = bundle.getString("address");
            paramDesc = bundle.getString("desc");
            bundle.clear();
            if(paramKeyword != null) {
                // 데이터 안건드리고 백버튼만 눌러 왔다갔다해도 번들 받아서 읽는 버그
                // 때문에 bundle.clear를 했는데 이렇게 하니 null 데이터가 쓰여짐
                // 그래서 null 검사하고 쓰기
                KeywordData data = new KeywordData(paramImageId, paramKeyword, paramAddress, paramDesc);
                keywords.addKeywordData(data);
                keywords.appendKeywordDataFile(getString(R.string.keywordData_filename), getContext());
            }
        }
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView_main);
        TextView textView = view.findViewById(R.id.textView_noItems);

        if(keywords.getKeywordDataList().size() == 0) {
            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        // RecyclerView
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        keywords.initAdapter();

        final KeywordAdapter adapter = keywords.getKeywordAdapter();
        recyclerView.setAdapter(adapter);
        // Add OnItemListener to items
        adapter.setOnItemListener(new KeywordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                KeywordData data = adapter.getItem(pos);
                CrawlOption.loadPreferences(getContext());
                int startPage = 1;
                int lastPage = startPage + CrawlOption.pagesPerCrawl - 1;
                ((MainActivity)getActivity()).crawl(data.getAddress(), data.getKeyword(), Integer.toString(startPage), Integer.toString(lastPage), "true");
            }
        });

        // RecyclerView Swipe Helper
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Log.e("RecyclerView", "onMove");
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                // TODO: Confirm for deletion?
                keywords.removeKeywordData(pos);
                keywords.appendKeywordDataFile(getString(R.string.keywordData_filename), getContext());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.addFragment:
                Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_addFragment);
                return true;
            case R.id.toolbar_sort:
                Context context = getContext();
                switch(sortMode) {
                    case 0:
                        keywords.sortByTitle();
                        sortMode++;
                        Snackbar.make(getView(), context.getString(R.string.toolbar_sortByTitle), Snackbar.LENGTH_SHORT).show();
                        break;
                    case 1:
                        keywords.sortByAddress();
                        sortMode++;
                        Snackbar.make(getView(), context.getString(R.string.toolbar_sortByAddress), Snackbar.LENGTH_SHORT).show();
                        break;
                    case 2:
                        keywords.sortByDesc();
                        sortMode++;Snackbar.make(getView(), context.getString(R.string.toolbar_sortByDesc), Snackbar.LENGTH_SHORT).show();
                        break;
                    default:
                        keywords.sortByTitle();
                        sortMode = 1;
                        Snackbar.make(getView(), context.getString(R.string.toolbar_sortByTitle), Snackbar.LENGTH_SHORT).show();
                        break;

                }
                keywords.getKeywordAdapter().notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
