package we.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.ParserConfigurationException;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by 一樹 on 2016/12/04.
 */

public class DisplaySearchResultsAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Book> bookArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;

    private static class ViewHolder2{
        TextView title;
        TextView author;
        NetworkImageView image;
    }

    /**
     * コンストラクタ
     * @param context　コンストラクタです。
     */
    public DisplaySearchResultsAdapter(Context context){
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext);
    }

    public Book getBook(int index){
        return this.bookArrayList.get(index);
    }

    @Override
    public int getCount(){
        return bookArrayList.size();
    }

    @Override
    public Object getItem(int position){
        return bookArrayList.get(position);
    }

    @Override
    public long getItemId(int posison){
        return posison;
    }

    //ListViewの中のアイテムを格納
    @Override
    public View getView(final  int position, View convertView  , ViewGroup parent){
        View view = convertView;
        DisplaySearchResultsAdapter.ViewHolder2 holder;

        //スクロースした時の高速化を図る
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_layout,parent,false);
            TextView title = (TextView)view.findViewById(R.id.title);
            TextView author = (TextView)view.findViewById(R.id.auther);
            NetworkImageView image = (NetworkImageView)view.findViewById(R.id.image);

            //holderにviewを持たせる
            holder = new DisplaySearchResultsAdapter.ViewHolder2();
            holder.title = title;
            holder.author = author;
            holder.image = image;
            view.setTag(holder);
        }else{//表示されたことがあるDisplayItemsだったら
            holder = (DisplaySearchResultsAdapter.ViewHolder2)view.getTag();
        }

        //Viewに反映
        holder.title.setText(bookArrayList.get(position).getTitle());
        //もし著作者が複数いた場合、「他とつける」
        String author = bookArrayList.get(position).getAuthor(0);
        if(bookArrayList.get(position).getAuthorLength() > 0){
            author += "　他..";
        }
        holder.author.setText(author);
        holder.image.setImageUrl(bookArrayList.get(position).getImage_URL(),Singleton.getInstance(mContext).getmImageLoader());
        return view;
    }

    /**
     * 国立国会図書館データベースからISNBで問い合わせ、その情報をBookとしてBooksDatabaseに格納、ListViewにアイテムを追加します
     * @param url　国立国会図書館へのURL
     */
    public void acquireBookFromeInternet(String url){
        progressDialog.setMessage("検索中です");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
        VolleyXmlRequest mRequest  = new VolleyXmlRequest(
                Request.Method.GET
                ,url
                , null
                , null
                , new Response.Listener<InputStream>() {
            @Override
            public synchronized void onResponse(InputStream response) {
                progressDialog.dismiss();
                ArrayList<Book> books = new ArrayList<>();
                try {
                    books = XmlParser.domParse(response);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                for(Book book : books){
                    if(!book.getTitle().equals("")){
                        if(book.getISBN().length() == 13 || book.getISBN().length() == 10) {
                            book.setImage_URL(UrlInformation.getImageURL(book.getISBN(), 3));
                        }
                        bookArrayList.add(book);
                    }
                }
                //あいうえお順にソート
                Collections.sort(bookArrayList, new Comparator<Book>() {
                    @Override
                    public int compare(Book book1,Book book2){
                        return book1.getTitle().compareTo((book2.getTitle()));
                    }
                });
                notifyDataSetChanged();
                notifyAll();
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 通信失敗時の処理を行なう...
                progressDialog.dismiss();
                Toast.makeText(mContext,"検索に失敗しました",LENGTH_LONG).show();
            }

        }
        );
        int custom_timeout_ms = 10000;
        DefaultRetryPolicy policy = new DefaultRetryPolicy(custom_timeout_ms,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequest.setRetryPolicy(policy);
        Singleton.getInstance(mContext).getRequestQueue().add(mRequest);

    }
}
