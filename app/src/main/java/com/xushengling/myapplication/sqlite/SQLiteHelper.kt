package com.xushengling.myapplication.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/**
 *
 * @ProjectName:    GitBoXueGu
 * @Package:        com.xushengling.myapplication.sqlite
 * @ClassName:      SQLiteHelper
 * @Author:         徐圣领
 * @CreateDate:     2020/4/22 下午 2:05
 */
class SQLiteHelper(context: Context?) : SQLiteOpenHelper(
        context,
        DB_NAME,
        null,
        DB_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        /**
         * 创建个人信息表
         */
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + U_USERINFO + "( "
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "userName VARCHAR, "
                    + "nickName VARCHAR, "
                    + "sex VARCHAR, "
                    + "signature VARCHAR"
                    + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP TABLE IF EXISTS $U_USERINFO")
        onCreate(db)
    }

    companion object {
        private const val DB_VERSION = 1
        var DB_NAME = "bxg.db"
        const val U_USERINFO = "userinfo"
    }
}