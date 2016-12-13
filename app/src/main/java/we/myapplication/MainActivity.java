package we.myapplication;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import android.content.Intent;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    /** 通信リクエスト */

    private RequestQueue mQueue;

    private int NumberOfBooks = 0;

    private final int RESULT_SEARCH = 10000;
    private static final int RC_BARCODE_CAPTURE = 9001;

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
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                //ListViewが持ってるBookを因数を渡す
                DetailDaialogFragment detailDaialogFragment = DetailDaialogFragment.newInstance(BooksDatabase.getInstance().get(position));
                detailDaialogFragment.show(getFragmentManager(),"TAG");
            }
        }



        );
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
            case R.id.menu_camera:
                Intent camera_intent = new Intent(this, BarcodeCaptureActivity.class);
                startActivityForResult(camera_intent, RC_BARCODE_CAPTURE);
                return true;

            case R.id.menu_search:
                Intent search_intent = new Intent(this, SearchActivity.class);
                int requestCode = RESULT_SEARCH;
                startActivityForResult(search_intent,requestCode);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //検索結果の表示
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(intent != null) {
            DisplayItemsAdapter adapter = DisplayItemsAdapter.getInscance();
            if(requestCode == RESULT_SEARCH && resultCode == Activity.RESULT_OK){
                BooksDatabase.getInstance().add( (Book)intent.getSerializableExtra("Result"));
                adapter.notifyDataSetChanged();

            }
            if (requestCode == RC_BARCODE_CAPTURE && resultCode == CommonStatusCodes.SUCCESS ) {
                Barcode barcode = intent.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                adapter.addItemsFromISNB(barcode.displayValue);
            }
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
        BooksDatabase.getInstance().saveToFile(this);
    }

}

