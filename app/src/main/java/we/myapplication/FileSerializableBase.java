package we.myapplication;

/**
 * Created by 一樹 on 2016/11/26.
 * 参考URL：http://qiita.com/fslasht/items/a54a6d00e5e64429f333
 */


import android.content.Context;
import android.support.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * ファイルへシリアライズ出来るクラスの基底クラス
 * このクラスを派生することで簡単にシリアライズされるクラスを作れる
 * @author F/T
 *
 */
public abstract class FileSerializableBase implements Serializable {
    private static final long serialVersionUID = 1L;

    ////////////////////////////////////////////////////////////////////////////////
    // ■ フィールド
    ////////////////////////////////////////////////////////////////////////////////

    // システム
    public static  int  _serialize_savecount_ = 0;                                      // セーブ回数
    transient static final  String _serialize_filename_ = "BooksData";                                      // ファイル名  ※保存はされない

    // ※ 派生クラスでシリアライズするフィールドを記述


    ////////////////////////////////////////////////////////////////////////////////
    // ■ 永続化
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * シリアライズしてファイルに保存
     * @param context   コンテキスト
     * @return  true:保存成功 false:保存失敗
     */
    protected boolean save(Context context) {
        _serialize_savecount_++;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(_serialize_filename_, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }finally{
            try {
                if ( oos!=null ) oos.close();
                if ( fos!=null ) fos.close();
            }catch(IOException ex ) {
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * ファイルからデシリアライズしてインスタンス生成
     * ※ファイルが存在シない場合は、初期値でインスタンスを生成
     * @param context   コンテキスト
     * @param clazz インスタンスを生成するクラス
     * @return 生成されたインスタンス
     */
    protected  FileSerializableBase newInstance(Context context , Class clazz ) {
        FileSerializableBase instance = null;   // 生成するインスタンス

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(_serialize_filename_);
            ois = new ObjectInputStream(fis);
            instance = (FileSerializableBase) ois.readObject();
        } catch (Exception ex) {
        }finally{
            try {
                if ( ois!=null ) ois.close();
                if ( fis!=null ) fis.close();
            }catch(IOException ex ) {
                ex.printStackTrace();
            }
        }

        if ( instance  ==null ) {
            // instanceがnullの場合は新規作成する
            try {
                instance = (FileSerializableBase)clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

        instance.loadAfter();       // メンバー 新規作成分の初期化

        return instance;
    }


    /**
     * デシリアライズ後の初期化
     * ※メンバーを新規作成した場合に、未初期化のメンバーを初期化する
     */
    public void loadAfter() {
    }

    ////////////////////////////////////////////////////////////////////////////////
    // ■ ファイル操作
    ////////////////////////////////////////////////////////////////////////////////

    /**
     // ファイル削除
     * @param context   コンテキスト
     */
    public void delete(Context context) {
        context.deleteFile(_serialize_filename_);
    }
}

