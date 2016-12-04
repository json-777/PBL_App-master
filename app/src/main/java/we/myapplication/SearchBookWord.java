package we.myapplication;

import java.net.URLEncoder;

/**
 * Created by 一樹 on 2016/12/04.
 */

public class SearchBookWord {
    String serchWord;
    Enum.Kind kind;

    public SearchBookWord(Enum.Kind kind,String serchWord ) {
        this.serchWord = serchWord;
        this.kind = kind;
    }

    /**
     * 検索文字列の取得
     *
     * @return 検索文字列
     */
    public String getSerchWord() {
        return this.serchWord;
    }

    /**
     * 種別の取得
     *
     * @return　種別の取得
     */
    public Enum.Kind getKind() {
        return this.kind;
    }

    /**
     * 検索文字列をURLエンコードする
     *
     * @return エンコード文字列
     */
    private String getEncode() {
        String encodedResult = "";
        if (kind == Enum.Kind.Isbn) {
            encodedResult = Convert13From10(this.serchWord);
        } else {
            try {
                encodedResult = URLEncoder.encode(this.serchWord, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return encodedResult;
    }

    public String getSerchWordURL() {
        String serchURL = getEncode();
        if (serchURL.equals("")) return "";
        return String.format("%s%s%s", getSerchKind(this.kind), serchURL, "%22");
    }

    private String getSerchKind(Enum.Kind kind) {
        switch (kind) {
            case Title:
                return "title%20%3d%20%22";
            case Creator:
                return "creator%20%3d%20%22";
            case Publisher:
                return "publisher%20%3d%20%22";
            case Isbn:
                return "isbn%20%3d%20%22";
            case From:
                return "from%20%3d%20%22";
        }
        return "";
    }

    /**
     * 13桁のISBN番号を10桁のISBN番号に変換する
     *
     * @param isbn ISBN番号
     * @return 10桁のISBN番号
     */
    private String Convert13From10(String isbn) {
        if (isbn.length() == 10)
            return isbn;
        int CheckDigit = 11 - Sum(isbn.substring(3, 12), 0) % 11;
        return isbn.substring(3, 12) + getChekDigit(CheckDigit);
    }

    /**
     * チェックディジットの取得
     *
     * @param c チェックディジットの変換
     * @return チェックディジット
     */
    private String getChekDigit(int c) {
        return c == 11 ? "0" : (c == 10 ? "X" : String.valueOf(c));
    }

    /**
     * @param isbn ISBN番号
     * @param time 回数
     * @return チェックディジットを求めるため合計値
     */
    private int Sum(String isbn, int time) {
        if (time == 9) return 0;
        return Integer.parseInt(isbn.substring(0, 1)) * (10 - time) + Sum(Cut(isbn), time + 1);
    }

    /**
     * 文字列の整形
     *
     * @param isbn ISBN番号
     * @return 先頭１ビット削除
     */
    private String Cut(String isbn) {
        return isbn.length() == 1 ? isbn : isbn.substring(1, isbn.length());
    }
}
