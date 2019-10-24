package com.radiantraccon.probe.data;

import java.util.ArrayList;

public class ResultDataListWrapper {
    private ArrayList<ResultData> resultDataList;
    private ResultAdapter resultAdapter;

    // getter
    public ArrayList<ResultData> getResultDataList() {
        return resultDataList;
    }
    public void setResultDataList(ArrayList<ResultData> resultDataList) {
        this.resultDataList = resultDataList;
    }
    // getter
    public ResultAdapter getResultAdapter() {
        return resultAdapter;
    }
    public void initResultAdapter() {
        this.resultAdapter = new ResultAdapter(resultDataList);
    }
}
