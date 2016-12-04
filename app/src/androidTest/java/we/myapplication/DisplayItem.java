package we.myapplication;

import android.media.Image;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by kazuki on 2016/09/25.
 */
public class DisplayItem {
    private String Title;
    private String Author;
    private String ImageURL;

    public String getTitle(){
        return  this.Title;
    }
    public void setTitle(String title){
        this.Title = title;
    }

    public String getAuthor(){
        return this.Author;
    }
    public void setAuthor(String author){
        this.Author = author;
    }

    public String getUrl(){
        return this.ImageURL;
    }
    public void setUrl(String url){
        this.ImageURL = url;
    }
}