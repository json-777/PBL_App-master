package we.myapplication;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by 一樹 on 2016/10/22.
 *VolleyのメッセージキューをSinglestonにするためのクラスです
 */
public class Singleton {
    private static Singleton ourInstance;
    private static RequestQueue mRequest;
    private static Context mContext;
    private static ImageLoader mImageLoader;


    public synchronized static Singleton getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new Singleton(context);
        }
        return ourInstance;
    }

    private Singleton(Context context){
        mContext = context;
        mRequest = getRequestQueue();
        mImageLoader = new ImageLoader(mRequest,new LruCacheSample());
    }
    public RequestQueue getRequestQueue(){
        if(mRequest == null){
            mRequest = Volley.newRequestQueue(mContext);
        }
        return mRequest;
    }
    public ImageLoader getmImageLoader(){
        return mImageLoader;
    }
}
