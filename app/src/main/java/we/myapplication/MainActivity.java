package we.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    /** 通信リクエスト */

    private RequestQueue mQueue;

    private int NumberOfBooks = 0;

    private final int RESULT_SEARCH = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ファイルからBookを読み込み
        BooksDatabase.getInstance().initializationFromFile(this);

        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.listView);
        mQueue = Volley.newRequestQueue(this);
        DisplayItemsAdapter adapter = new DisplayItemsAdapter(this.getApplicationContext());

        //ListViewのレイアウト設定
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                //ListViewが持ってるBookを因数を渡す
                DetailDaialogFragment detailDaialogFragment = DetailDaialogFragment.newInstance(BooksDatabase.getInstance().get(position));
                detailDaialogFragment.show(getFragmentManager(),"TAG");
            }
        }



        );
//        int padding = (int) (getResources().getDisplayMetrics().density * 8);
//        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
//        View header = inflater.inflate(R.layout.listview_header_footer,listView,false);
//        View footer = inflater.inflate(R.layout.listview_header_footer,listView,false);
//        listView.addHeaderView(header,null,false);
//        listView.addFooterView(footer,null,false);
//        listView.setDivider(null);
//        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
//        listView.setPadding(padding, 0, padding, 0);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // メニュー選択処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            /*
            case R.id.menu_camera:
                Intent intent = new Intent(this, hoge.class);
                startActivity(intent);
                return true;
            */

            case R.id.menu_search:
                Intent intent = new Intent(this, SearchActivity.class);
                int reuestCode = RESULT_SEARCH;
                startActivityForResult(intent,reuestCode);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //検索結果の表示
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(requestCode == RESULT_SEARCH && resultCode == Activity.RESULT_OK && intent != null){
            BooksDatabase.getInstance().add( (Book)intent.getSerializableExtra("Result"));
            DisplayItemsAdapter adapter = DisplayItemsAdapter.getInscance();
            adapter.notifyDataSetChanged();

        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        BooksDatabase.getInstance().saveToFile(this);
    }
    @Override
    public void onStop(){
        super.onStop();
//        Book book = new Book();
//        book.setTitle("aaaaaaa");
//        BooksDatabase.getInstance();
        BooksDatabase.getInstance().saveToFile(this);
    }

}

