package we.myapplication;
import java.util.ArrayList;

/**
 *
 * Created by kazuki on 2016/09/24.
 * 本の情報を格納していくクラスです。
 */
public class Book extends FileSerializableBase {
    private String Title = "";
    private ArrayList<String> AuthorList = new ArrayList<String>();
    private ArrayList<String> AuthorListKana = new ArrayList<String>();
    private String ISBN = "";
    private String Image_URL = "";
    private String Publisher = "";

    public String getPublisher(){
        return this.Publisher;
    }

    public void setPublisher(String Publisher){
        this.Publisher = Publisher;
    }

    public String getTitle(){
        return this.Title;
    }

    public String getISBN(){
        return this.ISBN;
    }

    public String getAuthor(int i){
        return this.AuthorList.get(i);
    }

    public String getAuthorKana(int i){
        return this.AuthorListKana.get(i);
    }

    public int getAuthorLength(){
        return this.AuthorList.size();
    }

    public String getImage_URL(){return Image_URL;}

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setISBN(String isbn) {
        this.ISBN = isbn;
    }

    public void addAuthor(String Author,String kana){
        this.AuthorList.add(Author);
        this.AuthorListKana.add(kana);
    }

    public void setImage_URL(String url){
        this.Image_URL = url;
    }

    @Override
    public void loadAfter(){}
}
