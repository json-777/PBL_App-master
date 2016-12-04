package we.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import  we.myapplication.UrlInformation;

/**
 * Created by kazuki on 2016/09/25.
 */
public class DisplayItemsAdapter extends BaseAdapter {
    private Context mContext;
    private static class ViewHolder{
        TextView title;
        TextView author;
        NetworkImageView image;
    }
    private static DisplayItemsAdapter sInstance;
    public static DisplayItemsAdapter getInscance(){
        return sInstance;
    }

    /**
     * コンストラクタ
     * @param context　コンストラクタです。
     */
    public DisplayItemsAdapter(Context context ){
        this.mContext = context;
        sInstance = this;
    }

    @Override
    public int getCount(){
        return BooksDatabase.getInstance().size();
    }

    @Override
    public Object getItem(int position){
        return BooksDatabase.getInstance().get(position);
    }

    @Override
    public long getItemId(int posison){
        return posison;
    }

    //ListViewの中のアイテムを格納
    @Override
    public View getView(final  int position, View convertView  , ViewGroup parent){
        View view = convertView;
        ViewHolder holder;

        //スクロースした時の高速化を図る
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_layout,parent,false);
            TextView title = (TextView)view.findViewById(R.id.title);
            TextView author = (TextView)view.findViewById(R.id.auther);
            NetworkImageView image = (NetworkImageView)view.findViewById(R.id.image);

            //holderにviewを持たせる
            holder = new ViewHolder();
            holder.title = title;
            holder.author = author;
            holder.image = image;
            view.setTag(holder);
        }else{//表示されたことがあるDisplayItemsだったら
            holder = (ViewHolder)view.getTag();
        }

        //Viewに反映
         holder.title.setText(BooksDatabase.getInstance().get(position).getTitle());
        //もし著作者が複数いた場合、「他とつける」
        String author = BooksDatabase.getInstance().get(position).getAuthor(0);
        if(BooksDatabase.getInstance().get(position).getAuthorLength() > 0){
            author += "　他..";
        }
         holder.author.setText(author);
         holder.image.setImageUrl(BooksDatabase.getInstance().get(position).getImage_URL(),Singleton.getInstance(mContext).getmImageLoader());
        return view;
    }

    /**
     * ISNB番号からListViewへのアイテムを追加します
     * @param ISNB ISNB番号
     */
    public void addItemsFromISNB(final String ISNB){
        acquireBookFromeInternet(UrlInformation.getBookUrlInfo(ISNB));
    }


    /**
     * 国立国会図書館データベースからISNBで問い合わせ、その情報をBookとしてBooksDatabaseに格納、ListViewにアイテムを追加します
     * @param url　国立国会図書館へのURL
     */
    private  void acquireBookFromeInternet(String url){
        VolleyXmlRequest mRequest  = new VolleyXmlRequest(
                Request.Method.GET
                ,url
                , null
                , null
                , new Response.Listener<InputStream>() {
            @Override
            public synchronized void onResponse(InputStream response) {
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
                        BooksDatabase.getInstance().add(book);
                    }
                }
                BooksDatabase.getInstance().sort();
                notifyDataSetChanged();
                notifyAll();
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 通信失敗時の処理を行なう...
            }

        }
        );
        Singleton.getInstance(mContext).getRequestQueue().add(mRequest);
    }



}
