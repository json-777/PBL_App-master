package we.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by 一樹 on 2016/11/26.
 */

public class SerializeBooks extends  FileSerializableBase {
    private ArrayList<Book> books = new ArrayList<Book>();

    public  SerializeBooks(){
        this.books = books;
    }

    public SerializeBooks getInstance(Context context){
        SerializeBooks sb = null;
        sb = (SerializeBooks)newInstance(context,this.getClass());
        return sb;
    }

    public void saveToFile(Context context,ArrayList<Book> books){
        this.books = books;
        this.save(context);
    }
    public ArrayList<Book> getBooks(){
        return this.books;
    }

    @Override
    public void loadAfter(){
    }

}
