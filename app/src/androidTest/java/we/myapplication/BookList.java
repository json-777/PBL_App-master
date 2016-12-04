package we.myapplication;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Version;

import java.util.List;

/**
 * Created by 一樹 on 2016/11/20.
 */
@Root
public class BookList {
    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public List<Book>getBooks() {
        return Books;
    }

    public void setBooks(List<Book> book) {
        Books = book;
    }

    /**XMLファイルのバージョンを示すプロパティ*/
    @Version(revision = 1.0)
    private  double version;

    /*本のリスト*/
    @ElementList
    private List<Book> Books;
}
