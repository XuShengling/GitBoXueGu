package com.xushengling.myapplication

import java.security.MessageDigest
import kotlin.experimental.and

/**
 *
 * @ProjectName:    BoXueGu
 * @Package:        com.xushengling.myapplication
 * @ClassName:      MD5Utils
 * @Author:         徐圣领
 * @CreateDate:     2020/3/9 14:58
 */
object MD5Utils {
    //MD5加密算法
    fun md5(text: String): String {
        val digest: MessageDigest?
        try {
            digest = MessageDigest.getInstance("md5")
            val result = digest.digest(text.toByteArray())
            val sb = StringBuilder()
            for (b in result) {
                val hex = Integer.toHexString((b and 0xff.toByte()).toInt())
                if (hex.length == 1) {
                    sb.append("0$hex")
                } else {
                    sb.append(hex)
                }
            }
            return sb.toString()
        } catch (e: EnumConstantNotPresentException) {
            e.printStackTrace()
            return ""
        }
    }
}