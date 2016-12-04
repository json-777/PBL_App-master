package we.myapplication;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by 一樹 on 2016/11/20.
 */
@Root
public class Authors  {
    public String getAuthor_Kana() {
        return Author_Kana;
    }

    public void setAuthor_Kana(String author_Kana) {
        Author_Kana = author_Kana;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    @Element
    private String Author;

    @Element
    private  String Author_Kana;

    public Authors(String Author,String Author_Kana){
        this.Author = Author;
        this. Author_Kana = Author_Kana;
    }
    public Authors(){}
}
