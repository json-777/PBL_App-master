package we.myapplication;

import java.util.ArrayList;
import java.net.URLEncoder;

/**
 * Created by 一樹 on 2016/11/26.
 */

public class UrlInformation {
    /**
     *13桁のISBN番号を10桁のISBN番号に変換する
     * @param isbn ISBN番号
     * @return 10桁のISBN番号
     */
    private static String Convert13From10(String isbn){
        if(isbn.length() == 10)
            return isbn;
        int CheckDigit = 11 - Sum(isbn.substring(3,12), 0) % 11;
        return isbn.substring(3,12) + getChekDigit(CheckDigit);
    }
    /**
     *
     * @param isbn ISBN番号
     * @param time 回数
     * @return チェックディジットを求めるため合計値
     */
    private static int Sum(String isbn,int time){
        if(time == 9) return 0;
        return Integer.parseInt(isbn.substring(0,1)) * (10 - time) + Sum(Cut(isbn), time+1);
    }

    /**
     * 文字列の整形
     * @param isbn ISBN番号
     * @return 先頭１ビット削除
     */
    private static String Cut(String isbn){
        return isbn.length() == 1 ? isbn : isbn.substring(1, isbn.length());
    }

    /**
     *チェックディジットの取得
     * @param c チェックディジットの変換
     * @return チェックディジット
     */
    private static String getChekDigit(int c){
        return c == 11 ? "0":(c == 10 ? "X" : String.valueOf(c));
    }

    /**
     *　国会図書館に問い合わせ用URLを取得する
     * @return 国会図書館に問い合わせ用URLを取得する(配列)
     */
    public static ArrayList<String> getBookUrlInfo(String[] ISBN){
        ArrayList<String> ISBNs = new ArrayList<String>();
        for(int i = 0; i < ISBN.length;i++){
            String isbn = ISBN[i];
            isbn = Convert13From10(isbn);
            ISBNs.add("http://iss.ndl.go.jp/api/sru?operation=searchRetrieve&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query=isbn%20%3d%20%22" + isbn + "%22");
        }
        return ISBNs;
        //  "http://iss.ndl.go.jp/api/sru?operation=searchRetrieve&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query=isbn%20%3d%20%22" + isbn + "%22";

    }
    /**
     *　国会図書館に問い合わせ用URLを取得する
     * @return 国会図書館に問い合わせ用URLを取得する(シングル)
     */
    public static String getBookUrlInfo(String ISBN){
        String isbn = Convert13From10(ISBN);
        return "http://iss.ndl.go.jp/api/sru?operation=searchRetrieve&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query=isbn%20%3d%20%22" + isbn + "%22";

        //  "http://iss.ndl.go.jp/api/sru?operation=searchRetrieve&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query=isbn%20%3d%20%22" + isbn + "%22";

    }

    /**
     * 画像を取得するためのURLを返します
     * @param isbn ISNB番号
     * @param lv 大きさ指定(小→大：0→3)
     * @return URL
     */
    public static String getImageURL(String isbn, int lv) {
        isbn = Convert13From10(isbn);
        String imageUrl = "http://images-jp.amazon.com/images/P/";
        switch (lv) {
            case 0:
                imageUrl = String.format("%s%s%s", imageUrl, isbn, ".09.THUMBZZZ.jpg");
                break;
            case 1:
                imageUrl = String.format("%s%s%s", imageUrl, isbn, ".09.TZZZZZZZ.jpg");
                break;
            case 2:
                imageUrl = String.format("%s%s%s", imageUrl, isbn, ".09.MZZZZZZZ.jpg");
                break;
            case 3:
                imageUrl = String.format("%s%s%s", imageUrl, isbn, ".09.LZZZZZZZ.jpg");
                break;
            default:
                return "";
        }
        return imageUrl;
    }


}
