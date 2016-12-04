package we.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 一樹 on 2016/10/27.
 * クラス「Book」のインスタンスを一括管理するクラスです。このクラスはシングルストンです。
 */
public class BooksDatabase  {
    private ArrayList<Book> Books ;
    private static BooksDatabase ourInstance = new BooksDatabase();

    /**
     * このクラスのインスタンスを返します
     * @return BooksDatabaseのインスタンス
     */
    public synchronized  static BooksDatabase getInstance() {
        if(ourInstance == null){
            ourInstance = new BooksDatabase();
        }
        return ourInstance;
    }

    /**
     * コンストラクタです。外部から参照できません
     */
    private BooksDatabase() {
        Books = new ArrayList<Book>();
    }

    /**
     * 格納されているすべてのBookインスタンスを返します
     * @return Bookインスタンス群
     */
    public ArrayList<Book> getBooks(){
        return this.Books;
    }

    /**
     * BooksDatabaseにBookインスタンスを追加します
     * @param book
     */
    public void add(Book book){
        Books.add(book);
    }

    /**
     * Bookインスタンス数を返します
     * @return　サイズ
     */
    public int size(){ return Books.size();}

    /**
     * indexで指定されたBookインスタンスを返します
     * @param index　インデックス
     * @return　Bookインスタンス
     */
    public Book get(int index){return this.Books.get(index);}

    /**
     * Booksの中で最後に格納されているインスタンスを返します。必ずnullチェックが必要です
     * @return　Bookインスタンス
     */
    public Book getLast(){
        if(Books.size() == 0)return null;
        return  Books.get(Books.size() - 1);
    }

    /**
     * Booksをあいうえお順にソートします
     */
    public void sort(){
        Collections.sort(this.Books, new Comparator<Book>() {
            @Override
            public int compare(Book book1,Book book2){
                return book1.getTitle().compareTo((book2.getTitle()));
            }
        });
    }

    /**
     * Booksをファイルに保存します
     * @param context アプリケーションコンテキスト
     */
    public void initializationFromFile(Context context){
        SerializeBooks sb = new SerializeBooks();
        sb = sb.getInstance(context);
        this.Books = sb.getBooks();
    }


    /**
     * ファイルからデシリアライズしてインスタンス化します
     * @param context　アプリケーションコンテキスト
     */
    public synchronized void saveToFile(Context context ){
        SerializeBooks sb = new SerializeBooks();
        sb.saveToFile(context,this.Books);
    }

    /**
     * シリアライズ・デシリアライズする対象のファイルを消去します
     */
    public synchronized  void deleateOfFile(Context context){
        SerializeBooks sb = new SerializeBooks();
        sb.delete(context);
    }





}
