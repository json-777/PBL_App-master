package we.myapplication;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.edit;
import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;

public class SearchActivity extends AppCompatActivity {

    private EditText title = null;
    private EditText author = null;
    private EditText isbn = null;
    private EditText publisher = null;
    private EditText published = null;
    private final int RESULT_SEARCH = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);
        final EditText title = (EditText)findViewById(R.id.tvBookTitle);
        final EditText author = (EditText)findViewById(R.id.tvBookAuthor);
        final EditText isbn = (EditText)findViewById(R.id.tvBookIsbn);
        final EditText publisher = (EditText)findViewById(R.id.tvBookPublisher);
        final EditText published = (EditText)findViewById(R.id.tvBookPublished);
        //ISBNに数字以外を入れられないようにする
        isbn.setInputType(TYPE_CLASS_NUMBER | TYPE_CLASS_TEXT);
        //改行禁止
        title.setInputType(TYPE_CLASS_TEXT);
        author.setInputType(TYPE_CLASS_TEXT);
        publisher.setInputType(TYPE_CLASS_TEXT);
        published.setInputType(TYPE_CLASS_TEXT);

        //タイプしたものによって入力禁止
        isbn.addTextChangedListener(new TextWatcher() {
            //ISBN番号を打った時はほかの検索項目は打てない
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //テキスト変更後
                if(s.length() == 0){
                    title.setEnabled(true);
                    author.setEnabled(true);
                    published.setEnabled(true);
                    publisher.setEnabled(true);
                    return;
                }
                title.setEnabled(false);
                author.setEnabled(false);
                published.setEnabled(false);
                publisher.setEnabled(false);
            }
        } );

        title.addTextChangedListener(new TextWatcher() {
            //ISBN番号を打った時はほかの検索項目は打てない
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //テキスト変更後
                if(s.length() == 0){
                    isbn.setEnabled(true);
                    return;
                }
                isbn.setEnabled(false);

            }
        } );
        published.addTextChangedListener(new TextWatcher() {
            //ISBN番号を打った時はほかの検索項目は打てない
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //テキスト変更後
                if(s.length() == 0){
                    isbn.setEnabled(true);
                    return;
                }
                isbn.setEnabled(false);

            }
        } );
        publisher.addTextChangedListener(new TextWatcher() {
            //ISBN番号を打った時はほかの検索項目は打てない
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //テキスト変更後
                if(s.length() == 0){
                    isbn.setEnabled(true);
                    return;
                }
                isbn.setEnabled(false);

            }
        } );

        author.addTextChangedListener(new TextWatcher() {
            //ISBN番号を打った時はほかの検索項目は打てない
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //テキスト変更後
                if(s.length() == 0){
                    isbn.setEnabled(true);
                    return;
                }
                isbn.setEnabled(false);

            }
        } );


        // ボタンオブジェクトの取得
        Button button = (Button) findViewById(R.id.tvBookSearch);
        // ボタンオブジェクトにクリックリスナー設定
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(published.getText().length() > 0 || publisher.getText().length() > 0){
                    if(title.getText().length() == 0 || author.getText().length() == 0){
                        String message = "出版社だけで検索はできません";
                        Toast.makeText(SearchActivity.this,message,Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // インテントへのインスタンス生成
                Intent intent = new Intent(SearchActivity.this, BookProfileActivity.class);


                //　インテントに値をセット
                intent.putExtra("bookTitle", title.getText().toString());
                intent.putExtra("bookAuthor", author.getText().toString());
                intent.putExtra("bookIsbn", isbn.getText().toString());
                intent.putExtra("bookPublisher", publisher.getText().toString());
                intent.putExtra("bookPublished", published.getText().toString());
                int requestCode = 2500;
                startActivityForResult(intent,requestCode);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode == 2500 && resultCode == Activity.RESULT_OK){
            Book book = (Book)intent.getSerializableExtra("Result");
            Intent rIntent = new Intent();
            rIntent.putExtra("Result",book);
            setResult(Activity.RESULT_OK,rIntent);
            finish();

        }
    }
}