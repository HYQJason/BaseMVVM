package com.hx.ice.util

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.hx.ice.util.ToastUtil.showShortCenter
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern

object StringUtil {
    const val EMPTY = ""
    fun isBlank(str: String?): Boolean {
        return str == null || "" == str
    }

    val uUId: String
        get() = UUID.randomUUID().toString().trim { it <= ' ' }.replace("-".toRegex(), "")

    fun changeStr(str: String?): String? {
        return if (isBlank(str)) {
            "--"
        } else str
    }

    //校验手机号码,精确地
    fun isLegalPhoneNumDetail(phoneNum: String): Boolean {
        return if (isBlank(phoneNum)) {
            false
        } else phoneNum.matches(Regex("^1\\d{10}$"))
    }

    //校验手机号码
    fun isLegalPhoneNum(phoneNum: String): Boolean {
        return if (isBlank(phoneNum)) {
            false
        } else phoneNum.matches(Regex("^1\\d{10}$"))
    }

    //校验身份证号码
    fun isLegalIdNum(idNum: String): Boolean {
        return if (isBlank(idNum)) {
            false
        } else idNum.matches(Regex("^\\d{17}(\\d|X|x)$"))
    }

    //校验中国用户名
    fun isFailChineseName(homeName: String): Boolean {
        return if (isBlank(homeName)) {
            false
        } else !homeName.matches(Regex("(?:[\u4E00-\u9FFF]{1,8}·\u4E00-\u9FFF]{1,8})|(?:[\u4E00-\u9FFF]{2,5})"))
    }

    //校验邮箱
    fun isLegalMailAddress(mail: String): Boolean {
        return if (isBlank(mail)) {
            false
        } else mail.matches(Regex("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"))
    }

    //校验银行卡号
    fun isLegalBankCardNo(bankNo: String): Boolean {
        return if (isBlank(bankNo)) {
            false
        } else bankNo.matches(Regex("^\\d{16,19}$"))
    }

    //混淆手机号码，中间添加*格式   133****1236
    fun blurPhoneNo(phoneNo: String): String? {
        if (isBlank(phoneNo)) {
            return phoneNo
        } else if (isLegalPhoneNum(phoneNo)) {
            return phoneNo.substring(0, 3) + "****" + phoneNo.substring(7, 11)
        }
        return null
    }

    //混淆身份证号，中间添加*格式   133123*********236
    fun blurIdCardNo(idNo: String): String? {
        if (isBlank(idNo)) {
            return idNo
        } else if (isLegalIdNum(idNo)) {
            return (idNo.substring(0, 4) + "**********"
                    + idNo.substring(idNo.length - 4, idNo.length))
        }
        return null
    }

    //混淆银行卡号，中间添加*格式   6228 **** **** 6217
    fun blurBankCardNo(bankNo: String): String? {
        if (isBlank(bankNo)) {
            return bankNo
        } else if (isLegalBankCardNo(bankNo)) {
            return (bankNo.substring(0, 4) + " **** **** "
                    + bankNo.substring(bankNo.length - 4, bankNo.length))
        }
        return null
    }

    //是否是合法的充值金额，是大于0的正整数
    fun isLegalAmount(amount: String): Boolean {
        val regPaw = "^[1-9]\\d*$"
        return amount.matches(Regex(regPaw))
    }

    /**
     * 保留小数点后两位
     *
     * @param value
     * @return
     */
    fun round(value: String?): String {
        val scale = 2
        val roundingMode = BigDecimal.ROUND_HALF_EVEN
        var decimal = BigDecimal(value)
        decimal = decimal.setScale(scale, roundingMode)
        return decimal.toString()
    }

    /**
     * 正则判断密码特殊字符
     *
     * @param password
     * @return
     */
    fun regPassword(password: String): Boolean {
//        String regPaw = "[a-zA-Z0-9~!@#$^*.]+";
        val regPaw =
            "^(?![A-Za-z]+$)(?![A-Z\\d]+$)(?![A-Z\\W]+$)(?![a-z\\d]+$)(?![a-z\\W]+$)(?![\\d\\W]+$)\\S{8,16}$"
        return password.matches(Regex(regPaw))
    }

    /**
     * 正则判断邀请码特殊字符
     *
     * @param inviteCode
     * @return
     */
    fun regInviteCode(inviteCode: String): Boolean {
        val regPaw = "^[0-9a-zA-Z]{6}$"
        return inviteCode.matches(Regex(regPaw))
    }

    /**
     * 返回格式化金钱
     *
     * @param money
     * @return
     */
    fun getMoneyFormat(money: String?): String {
        return try {
            val `in` = BigDecimal(money)
            val out =
                DecimalFormat("#,###,###,###,###,###,##0.00")
            out.format(`in`)
        } catch (e: NumberFormatException) {
            "0"
        }
    }

    /**
     * 返回格式化金钱不带最后两个0
     *
     * @param money
     * @return
     */
    fun getMoneyFormatWithoutEnd(money: String?): String {
        return try {
            val `in` = BigDecimal(money)
            val out = DecimalFormat("#,###,###,###,###,###,##0")
            out.format(`in`)
        } catch (e: NumberFormatException) {
            "0"
        }
    }

    /**
     * 组成两种颜色的字符串
     *
     * @param context
     * @param s
     * @param index   从第几个字开始变色
     * @param color1  第一种颜色id
     * @param color2  第二种颜色id
     * @return
     */
    fun changeStringColor(
        context: Context?,
        s: String,
        index: Int,
        @ColorRes color1: Int,
        @ColorRes color2: Int
    ): SpannableString {
        if (s.length < index) {
            return SpannableString(s)
        }
        val content = SpannableString(s)
        content.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context!!, color1)),
            0,
            index,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        content.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, color2)),
            index,
            s.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return content
    }

    /**
     * 改变从index开始的字符大小
     *
     * @param s
     * @param index        从第几个字符开始
     * @param relativeSize 相对大小倍数
     * @return
     */
    fun changeStringSize(
        s: String,
        index: Int,
        relativeSize: Float
    ): SpannableString {
        if (s.length < index) {
            return SpannableString(s)
        }
        val content = SpannableString(s)
        content.setSpan(
            RelativeSizeSpan(relativeSize),
            index,
            s.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return content
    }

    /**
     * 验证正整数
     *
     * @param str
     * @return
     */
    fun isInteger(str: String?): Boolean {
        val pattern = Pattern.compile("^[-\\+]?[\\d]*$")
        return pattern.matcher(str).matches()
    }

    /**
     * 返回后四个字符串
     *
     * @param s
     * @return
     */
    fun end4(s: String): String {
        val i = s.length
        return if (i <= 4) {
            s
        } else {
            s.substring(i - 4, i)
        }
    }

    /**
     * 检查手机号是否符合规则,带提示
     *
     * @param phone
     * @return
     */
    fun checkPhoneNumber(phone: String): Boolean {
        if (TextUtils.isEmpty(phone)) {
            showShortCenter("请输入您的手机号")
            return false
        }
        if (!isLegalPhoneNum(phone)) {
            showShortCenter("手机号输入错误")
            return false
        }
        return true
    }

    /**
     * 检查密码是否符合规则,带提示
     * @param password
     * @return
     */
    fun checkPassword(password: String): Boolean {
        if (TextUtils.isEmpty(password)) {
            showShortCenter("请输入密码")
            return false
        }
        if (!regPassword(password)) {
            showShortCenter("密码必须为8~16\n字母大小写和数字的组合。\n例如:Abc59024等")
            return false
        }
        return true
    }

    fun checkPassword1(password: String): Boolean {
        if (TextUtils.isEmpty(password)) {
            showShortCenter("请输入密码")
            return false
        }
        if (password.length < 8 || password.length > 16) {
            showShortCenter("密码必须为8~16字母，数字，部分符号")
            return false
        }
        return true
    }



    fun getUrl(
        url: String?,
        params: Map<String?, String?>?
    ): String? {
        // 添加url参数
        var url = url
        if (params != null) {
            val it = params.keys.iterator()
            var sb: StringBuffer? = null
            while (it.hasNext()) {
                val key = it.next()
                val value = params[key]
                if (sb == null) {
                    sb = StringBuffer()
                    sb.append("?")
                } else {
                    sb.append("&")
                }
                sb.append(key)
                sb.append("=")
                sb.append(value)
            }
            url += sb.toString()
        }
        return url
    }

    /**
     * 提取数字
     *
     * @param content
     * @return
     */
    fun getNumbers(content: String?): String {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(content)
        return m.replaceAll("").trim { it <= ' ' }
    }

    /**
     * 替换字符串中的“/”
     *
     * @param s
     * @return
     */
    fun replaceSprit(s: String): String {
        var s = s
        if (s.contains("/")) {
            s = s.replace("/", "_")
        }
        return s
    }

    /**
     * 转换String为int
     *
     * @param s
     * @return
     */
    fun tranStr2Int(s: String?): Int {
        return try {
            Integer.valueOf(s)
        } catch (e: NumberFormatException) {
            0
        }
    }

    /**
     * 转换String为int
     *
     * @param s
     * @return
     */
    fun tranStr2Double(s: String?): Double {
        return try {
            java.lang.Double.valueOf(s)
        } catch (e: NumberFormatException) {
            0.0
        }
    }

    /**
     * 转换String为float
     *
     * @param s
     * @return
     */
    fun tranStr2Float(s: String?): Float {
        return try {
            java.lang.Float.valueOf(s)
        } catch (e: NumberFormatException) {
            0f
        }
    }

    /**
     * 保留小数，四舍五入
     * @param d
     * @param i
     * @return
     */
    fun scaleDouble(d: Double, i: Int): Double {
        var d = d
        val bg = BigDecimal(d)
        d = bg.setScale(i, BigDecimal.ROUND_HALF_UP).toDouble()
        return d
    }

    /**
     * 保留两位小数
     * @param d
     * @return
     */
    fun formatDouble(d: Double): String {
        val df = DecimalFormat("#0.00")
        return df.format(d)
    }

    /**
     * 格式化银行卡号，四位隔开
     * @param bankNumber
     * @return
     */
    fun formatBankNumber(bankNumber: String?): String? {
        if (TextUtils.isEmpty(bankNumber)) return bankNumber
        var index = 0
        val buffer = StringBuffer(bankNumber)
        while (index < buffer.length) {
            if (index == 4 || index == 9 || index == 14 || index == 19 || index == 24 || index == 29) {
                buffer.insert(index, ' ')
            }
            index++
        }
        return buffer.toString()
    }

    /**
     *
     * 格式化银行卡号，前五位隔开，之后每四位隔开
     * @param bankNumber
     * @return
     */
    fun formatBankNumberFirst5(bankNumber: String?): String? {
        if (TextUtils.isEmpty(bankNumber)) return bankNumber
        var index = 0
        val buffer = StringBuffer(bankNumber)
        while (index < buffer.length) {
            if (index == 5 || index == 10 || index == 15 || index == 20 || index == 25 || index == 30) {
                buffer.insert(index, ' ')
            }
            index++
        }
        return buffer.toString()
    }
}