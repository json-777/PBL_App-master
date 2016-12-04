package we.myapplication;

import java.util.ArrayList;

/**
 * Created by 一樹 on 2016/12/04.
 */

public class SearchBookInfo {
    /**
     * 国会図書館に問い合わせを行いXML形式
     *
     * @param arrayList Bookの配列
     * @return　検索結果
     */
    public String getBookInfo(ArrayList<SearchBookWord> arrayList) {
        String encodedResult = "";
        for (int i = 0; i < arrayList.size(); i++) {
            String word = arrayList.get(i).getSerchWordURL();
            encodedResult = String.format("%s%s%s", encodedResult, i == 0 ? "":"%20AND%20", (word.equals("") ? "" : word));
        }
        String url = String.format("%s%s", "http://iss.ndl.go.jp/api/sru?operation=searchRetrieve&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query=", encodedResult);
        return url;
    }

    /**
     *  検索結果の続きを取得する
     * @param url 検索クエリ
     * @param start 何件目から取得するか
     * @param max 検索取得数
     * @return
     */
    public String getBookInfoAdd(String url,int start,int max){
        return String.format("%s&startRecord=%d&maximumRecords=%d",url,start,max);
    }
}
