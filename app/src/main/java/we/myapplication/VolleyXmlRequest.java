package we.myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by kazuki on 2016/09/24.
 */

/*
* 9行目～21行目のコンストラクタで下記の６項目を設定します。
①リクエストメソッド
 リクエストメソッドはGETで通信する場合はRequest.Method.GET。POSTで通信する場合はRequest.Method.POSTになります。

②通信用URL
通信を行なう為のURL。

③ヘッダー情報
VolleyによるBasic(基本)認証処理で解説したようにヘッダーに情報を設定する場合に使用します。ヘッダーに特に情報を設定しない場合はnullを渡します。

④パラメータ情報
②の通信用URLに対してパラメータを付加する場合に使用します。パラメータを特に設定しない場合はnullを渡します。

⑤レスポンス用リスナー
通信先に正常に接続できた以降での通信先からのレスポンス受信用リスナーです。

⑥エラー用リスナー
通信先に正常に接続できない場合にコールされるエラー用リスナーです。

33行目～37行目で受信したレスポンスデータをストリーム型に変換して返却しています。これにより上記の⑤レスポンス用リスナーにストリーム型で返却されるようになります。

* */
public class VolleyXmlRequest extends Request<InputStream> {
    private Response.Listener<InputStream> mListener;
    /** ヘッダー情報 */
    private Map<String, String> mHeaders;
    /** POST用データ */
    private Map<String, String> mParams;


    public VolleyXmlRequest(
            int method,
            String url,
            Map<String, String> headers,
            Map<String, String> params,
            Response.Listener<InputStream> listener,
            Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        mListener = listener;
        mHeaders = headers;
        mParams = params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams != null ? mParams : super.getParams();
    }

    @Override
    protected Response<InputStream> parseNetworkResponse(NetworkResponse response) {
        InputStream is = new ByteArrayInputStream(response.data);
        return Response.success(is, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(InputStream response) {
        mListener.onResponse(response);
    }
}


