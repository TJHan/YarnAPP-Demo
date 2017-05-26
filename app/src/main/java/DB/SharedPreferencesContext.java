package DB;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tjhan on 2017-05-24.
 * SharedPreferences存储类
 */

public class SharedPreferencesContext {

    private Context mContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesContext(Context context, String dbName) {
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean Save(String key, String value) {
        if (null == editor)
            return false;
        try {
            editor.putString(key, value);
            editor.apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取数据
     * @param key
     * @return
     */
    public String Read(String key) {
        if (null == editor)
            return null;
        String result = sharedPreferences.getString(key, "");
        return result;
    }


}
