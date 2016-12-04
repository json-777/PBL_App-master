package we.myapplication;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.zip.InflaterInputStream;

import javax.xml.parsers.ParserConfigurationException;

import static we.myapplication.XmlParser.*;

/**
 * このクラスはISBNコードの配列を与えるとBookインスタンスの変数全てに値を代入してBookの配列をArrayListとして返します
 * Created by kazuki on 2016/09/26.
 */
 class ListViewChange {
    private static DisplayItemsAdapter mAdapter;
    private static Context mContext;
    private Book bookbuf;

    /**
     * コンストラクタです
     * @param context このアプリケーションのコンテキスト
     * @param adapter
     */
    public   ListViewChange(Context context,DisplayItemsAdapter adapter) {
        mAdapter = adapter;
        mContext = context;
    }



//
//
//
//
//    private synchronized void sendRequest(String url ){
//        VolleyXmlRequest mRequest  = new VolleyXmlRequest(
//                Request.Method.GET
//                ,url
//                , null
//                , null
//                , new Response.Listener<InputStream>() {
//            @Override
//            public  void onResponse(InputStream response) {
//                XmlParser parser = new XmlParser();
//                Book book = new Book();
//                try {
//                    book = parser.domParse(response);
//                } catch (SAXException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ParserConfigurationException e) {
//                    e.printStackTrace();
//                }
//                String a = book.getISBN();
////                bookbuf.setTitle(book.getTitle());
////                for(int i = 0;i < book.getAuthorLength();i++){
////                    bookbuf.addAuthor(book.getAuthor(i),book.getAuthorKana(i));
////                }
////                mAdapter.addDisplayItem(bookbuf);
////                bookbuf = null;
//            }
//        }
//                , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // 通信失敗時の処理を行なう...
//            }
//
//        }
//        );
//        Singleton.getInstance(mContext).getRequestQueue().add(mRequest);
//
//    }
//
//    /**
//     * 表紙画像を取得のためのURLを返します
//     * @param lv 大きさ指定(小→大：0→3)
//     * @return　書籍の表紙情報URL
//     */
//    public static String getImageURL(String isbn, int lv) {
//        isbn = Convert13From10(isbn);
//        String imageUrl = "http://images-jp.amazon.com/images/P/";
//        switch (lv) {
//            case 0:
//                imageUrl = String.format("%s%s%s", imageUrl, isbn, ".09.THUMBZZZ.jpg");
//                break;
//            case 1:
//                imageUrl = String.format("%s%s%s", imageUrl, isbn, ".09.TZZZZZZZ.jpg");
//                break;
//            case 2:
//                imageUrl = String.format("%s%s%s", imageUrl, isbn, ".09.MZZZZZZZ.jpg");
//                break;
//            case 3:
//                imageUrl = String.format("%s%s%s", imageUrl, isbn, ".09.LZZZZZZZ.jpg");
//                break;
//            default:
//                return "";
//        }
//        return imageUrl;
//    }







}
