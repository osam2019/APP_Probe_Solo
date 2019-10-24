package com.radiantraccon.probe.site;

import android.util.Log;

import com.radiantraccon.probe.data.ResultData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Quasarzone {
    public static final String MAIN_PAGE = "https://quasarzone.co.kr/";
    public static final String NEWS_GAME = "https://quasarzone.co.kr/bbs/board.php?bo_table=qn_game";
    public static final String NEWS_HARDWARE = "https://quasarzone.co.kr/bbs/board.php?bo_table=qn_hardware";
    public static final String NEWS_MOBILE = "https://quasarzone.co.kr/bbs/board.php?bo_table=qn_mobile";
    public static final String NEWS_SALE = "https://quasarzone.co.kr/bbs/board.php?bo_table=qb_saleinfo";
    public static final String FAVICON = "https://quasarzone.co.kr/favicon.ico";
    public static final String PAGE = "&page=";

    public static ArrayList<ResultData> getData(String address, String keyword, int page) {
        ArrayList<ResultData> ret = new ArrayList<>();
        try {
            Document document = Jsoup.connect(address + PAGE + page).get();
            Elements elements = document
                    .select("ul[class=list-body]")
                    .select("li[class=list-item]");
            int size = elements.size();

            for(Element e : elements) {
                Elements temp = e.select("div[class=wr-subject]").select("a[class=item-subject]");

                String imageUrl = FAVICON;
                String title = temp.first().ownText();

                if(!title.contains(keyword)) {
                    continue;
                }

                String desc = e.select("div[class=wr-category fs11 hidden-xs]").text();
                String link = temp.attr("href");
                Log.e("Result Data", link);
                ret.add(new ResultData(imageUrl, title, link, desc));
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
