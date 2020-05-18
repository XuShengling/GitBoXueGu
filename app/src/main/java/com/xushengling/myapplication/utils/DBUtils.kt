package com.xushengling.myapplication.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.xushengling.myapplication.bean.UserBean
import com.xushengling.myapplication.sqlite.SQLiteHelper

/**
 *
 * @ProjectName:    GitBoXueGu
 * @Package:        com.xushengling.myapplication.utils
 * @ClassName:      DBUtils
 * @Author:         徐圣领
 * @CreateDate:     2020/4/22 下午 8:08
 */
class DBUtils(context: Context) {
    /**
     * 保存个人信息
     */
    fun saveUserInfo(bean: UserBean?) {
        val cv = ContentValues()
        cv.put("userName", bean!!.userName)
        cv.put("nickName", bean.nickName)
        cv.put("sex", bean.sex)
        cv.put("signature", bean.signature)
        db!!.insert(SQLiteHelper.U_USERINFO, null, cv)
    }

    /**
     * 获取个人信息
     */
    fun getUserInfo(userName: String?): UserBean? {
        val sql = "SELECT * FROM ${SQLiteHelper.U_USERINFO} WHERE userName=?"
        val cursor = db!!.rawQuery(sql, arrayOf(userName))
        var bean: UserBean? = null
        while (cursor.moveToNext()) {
            bean = UserBean()
            bean.userName = cursor.getString(cursor.getColumnIndex("userName"))
            bean.nickName = cursor.getString(cursor.getColumnIndex("nickName"))
            bean.sex = cursor.getString(cursor.getColumnIndex("sex"))
            bean.signature = cursor.getString(cursor.getColumnIndex("signature"))
        }
        cursor.close()
        return bean
    }

    /**
     * 修改个人资料
     */
    fun updateUserInfo(key: String,value:String, userName: String?) {
        val cv = ContentValues()
        cv.put(key, value)
        db!!.update(SQLiteHelper.U_USERINFO, cv, "userName=?", arrayOf(userName))
    }

    companion object {
        private var instance: DBUtils? = null
        var db: SQLiteDatabase? = null
        fun getInstance(context: Context): DBUtils {
            if (instance == null) instance = DBUtils(context)
            return instance!!
        }
    }

    init {
        db = SQLiteHelper(context).writableDatabase
    }
}
