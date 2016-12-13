package we.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import we.myapplication.Enum;

public class BookProfileActivity extends AppCompatActivity {
    private final int RESULT_SEARCH = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);
        ArrayList<SearchBookWord> maps = new ArrayList<>();
        Intent intent = getIntent();
        String buf = "";
        if(!(buf = intent.getStringExtra("bookTitle")).equals("")){
            maps.add(new SearchBookWord(Enum.Kind.Title,buf));
        }
        if(!(buf = intent.getStringExtra("bookAuthor")).equals("")){
            maps.add(new SearchBookWord(Enum.Kind.Creator,buf));
        }
        if(!(buf = intent.getStringExtra("bookIsbn")).equals("")){
            maps.add(new SearchBookWord(Enum.Kind.Isbn,buf));
        }
        if(!(buf = intent.getStringExtra("bookPublisher")).equals("")){
            maps.add(new SearchBookWord(Enum.Kind.Publisher,buf));
        }
        if(!(buf = intent.getStringExtra("bookPublished")).equals("")){
            maps.add(new SearchBookWord(Enum.Kind.From,buf));
        }
        SearchBookInfo info = new SearchBookInfo();
        String url = info.getBookInfo(maps);
        url = info.getBookInfoAdd(url,1,25);

        ListView listView = (ListView)findViewById(R.id.ResultListView);
        final DisplaySearchResultsAdapter adapter = new DisplaySearchResultsAdapter(BookProfileActivity.this);
        //Litviewのアイテムがクリックされたときに呼ばれるコールバックをセット
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                                            @Override
                                            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                                                ResultDaialogFragment daialogFragment = ResultDaialogFragment.newInstance(adapter.getBook(position));
                                                daialogFragment.show(getFragmentManager(),"TAG2");


                                            }
                                        });

        listView.setAdapter(adapter);
        adapter.acquireBookFromeInternet(url);




    }
    public void onBackButtonClick(View view) {
        finish();
    }
    public void onReturnValue(Book book){
        if(book != null){
            Intent intent = new Intent();
            intent.putExtra("Result",book);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }


    }