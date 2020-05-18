package com.xushengling.javaboxuegu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xushengling.javaboxuegu.bean.UserBean;
import com.xushengling.javaboxuegu.sqlite.SQLiteHelper;

/**
 * @ProjectName: GitBoXueGu
 * @Package: com.xushengling.javaboxuegu.utils
 * @ClassName: DBUtils
 * @Author: 徐圣领
 * @CreateDate: 2020/4/26 下午 1:36
 */
public class DBUtils {
    private static DBUtils instance = null;
    private static SQLiteDatabase db;

    private DBUtils(Context context) {
        SQLiteHelper helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public static DBUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DBUtils(context);
        }
        return instance;
    }

    /**
     * 保存个人资料信息
     */
    public void saveUserInfo(UserBean bean) {
        ContentValues cv = new ContentValues();
        cv.put("userName", bean.userName);
        cv.put("nickName", bean.nickName);
        cv.put("sex", bean.sex);
        cv.put("signature", bean.signature);
        db.insert(SQLiteHelper.U_USERINFO, null, cv);
    }

    /**
     * 获取个人资料信息
     */
    public UserBean getUserInfo(String userName) {
        String sql = "SELECT * FROM " + SQLiteHelper.U_USERINFO + " WHERE userName =?";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        UserBean bean = null;
        while (cursor.moveToNext()) {
            bean = new UserBean();
            bean.userName = cursor.getString(cursor.getColumnIndex("userName"));
            bean.nickName = cursor.getString(cursor.getColumnIndex("nickName"));
            bean.sex = cursor.getString(cursor.getColumnIndex("sex"));
            bean.signature = cursor.getString(cursor.getColumnIndex("signature"));
        }
        cursor.close();
        return bean;
    }

    /**
     * 修改个人资料
     */
    public void updateUserInfo(String key, String value, String userName) {
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(SQLiteHelper.U_USERINFO, cv, "userName=?", new String[]{userName});
    }
}
