package we.myapplication;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

/**
 * Created by 一樹 on 2016/11/27.
 * ListViewをタップしたと時に表示するダイアログを設定します。
 *
 * */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class DetailDaialogFragment extends DialogFragment {
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    static DetailDaialogFragment newInstance(Book book){
        DetailDaialogFragment f = new DetailDaialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("book",book);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containewr,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.detail_daialog,containewr,false);
        Bundle arguments = getArguments();
        if(arguments != null){
            //Bundleに保存したBookを取得する
            Book book = (Book)arguments.getSerializable("book");
            if(book != null){


                TextView text1 = (TextView)v.findViewById(R.id.text1);
                text1.setText("タイトル：");
                TextView text2 = (TextView)v.findViewById(R.id.text2);
                text2.setText("著作者　：");
                TextView text3 = (TextView)v.findViewById(R.id.text3);
                text3.setText("出版社　：");

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
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityCreated(Bundle savedInstanceStade){
        super.onActivityCreated(savedInstanceStade);

        //ダイアログサイズ変更
        Dialog dialog = getDialog();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

        DisplayMetrics metrics= getResources().getDisplayMetrics();
        int dialogWidth = (int)(metrics.widthPixels);
        int dialogHeight = (int)(metrics.heightPixels * 0.8);

        lp.width = dialogWidth;
        lp.height = dialogHeight;
        dialog.getWindow().setAttributes(lp);
    }

}
