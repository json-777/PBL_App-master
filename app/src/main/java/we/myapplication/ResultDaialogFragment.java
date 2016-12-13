package we.myapplication;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by 一樹 on 2016/11/27.
 * ListViewをタップしたと時に表示するダイアログを設定します。
 * 結果表示の時に使用します
 *
 * */

public class ResultDaialogFragment extends DetailDaialogFragment {
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    static ResultDaialogFragment newInstance(Book book){
        ResultDaialogFragment f = new ResultDaialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("book",book);
        f.setArguments(args);
        return  f;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup containewr, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dailog_result,containewr,false);
        Bundle arguments = getArguments();
        if(arguments != null){
            //Bundleに保存したBookを取得する
            final Book book = (Book)arguments.getSerializable("book");
            if(book != null){
                TextView text1 = (TextView)v.findViewById(R.id.text1);
                text1.setText("タイトル：");
                TextView text2 = (TextView)v.findViewById(R.id.text2);
                text2.setText("著作者　：");
                TextView text3 = (TextView)v.findViewById(R.id.text3);
                text3.setText("出版社　：");
                Button button = (Button)v.findViewById(R.id.result_dialog_button);
                //登録ボタンclick時の処理→MainActivity検索結果を返す
                button.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void onClick(View v) {
                        BookProfileActivity callingActivity = (BookProfileActivity)getActivity();
                        callingActivity.onReturnValue(book);
                    }
                });

                NetworkImageView networkImageView = (NetworkImageView)v.findViewById(R.id.newtwordImage);
                TextView title = (TextView)v.findViewById(R.id.title);
                TextView auther = (TextView)v.findViewById(R.id.author);
                TextView publisher = (TextView)v.findViewById(R.id.publisher);
                ImageLoader loader = Singleton.getInstance(null).getmImageLoader();
                networkImageView.setImageUrl(book.getImage_URL(),loader);
                title.setText(book.getTitle());
                String authers = book.getAuthor(0);
                for(int i = 1;i < book.getAuthorLength();i++){
                    authers += "," + book.getAuthor(i);
                }
                auther.setText(authers);
                publisher.setText(book.getPublisher());
            }
        }
        return v;
    }
}