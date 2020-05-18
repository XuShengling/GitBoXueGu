package com.xushengling.javaboxuegu.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @ProjectName: GitBoXueGu
 * @Package: com.xushengling.javaboxuegu.sqlite
 * @ClassName: SQLiteHelper
 * @Author: 徐圣领
 * @CreateDate: 2020/4/22 下午 2:12
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static String DB_NAME = "bxg.db";
    public static final String U_USERINFO = "userinfo";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    /**
     * 创建个人信息表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + U_USERINFO + "( "
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userName VARCHAR, "
                + "nickName VARCHAR, "
                + "sex VARCHAR, "
                + "signature VARCHAR"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + U_USERINFO);
        onCreate(db);
    }
}
