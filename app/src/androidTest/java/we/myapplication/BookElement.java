package we.myapplication;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by 一樹 on 2016/11/20.
 */
@Root
public class BookElement {
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<we.myapplication.Authors> getAuthors() {
        return Authors;
    }

    public void setAuthors(List<we.myapplication.Authors> authors) {
        Authors = authors;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public void setImage_Url(String image_Url) {
        Image_Url = image_Url;
    }

    @Element
    private  String Title;

    @ElementList
    private List<Authors> Authors;

    @Element
    private  String ISBN;

    @Element
    private  String Publisher;

    @Element
    private  String Image_Url;

    public BookElement(){}

    public BookElement(String Title,List<Authors> Authors,String ISBN,String Publisher,String Image_Url){
        this.Title = Title;
        this.Authors = Authors;
        this.ISBN = ISBN;
        this.Publisher = Publisher;
        this.Image_Url = Image_Url;
    }
}
