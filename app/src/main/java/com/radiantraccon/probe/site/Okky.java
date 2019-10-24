package com.radiantraccon.probe.site;

import android.util.Log;

import com.radiantraccon.probe.data.ResultData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Okky {
    public static final String MAIN_PAGE = "https://okky.kr";
    public static final String TECH = "https://okky.kr/articles/tech";
    public static final String COLUMS = "https://okky.kr/articles/columns";
    public static final String JOBS = "https://okky.kr/articls/jobs";
    public static final String FAVICON = "https://okky.kr/assets/favicon-4ddd8035b72404da5a8c298cbaacad86.ico";
    public static final String PAGE = "?offset=";


    public static ArrayList<ResultData> getData(String address, String keyword, int page) {
        ArrayList<ResultData> ret = new ArrayList<>();
        try {
            Document document = Jsoup.connect(address + PAGE + page*20).get();
            Elements elements = document
                    .select("ul[class=list-group]")
                    .select("li");

            for (Element e : elements) {
                Elements temp = e
                        .select("div[class=list-title-wrapper clearfix]")
                        .select(("h5[class=list-group-item-heading list-group-item-evaluate]"));

                String imageUrl = FAVICON;
                String title = temp.text();

                if (!title.contains(keyword)) {
                    continue;
                }

                String desc = e.select("div[class=list-tag clearfix]").text();
                String link = MAIN_PAGE + (temp.select("a").attr("href"));
                Log.e("Result Data", "\nimageURL: "+imageUrl+"\nTitle: "+title+"\nLink: "+link+"\nDesc: "+desc);
                ret.add(new ResultData(imageUrl, title, link, desc));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
