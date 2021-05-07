package com.xbmm.http.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.hx.ice.base.PermissionUtils
import com.xbmm.http.constants.HttpConstants.deviceType
import com.xbmm.http.constants.HttpConstants.deviceUUID
import java.util.*

/**
 * 通过Android_Id获取设备唯一标识(无需权限申请)
 * 问题： 厂商返回null或9774d56d682e549c
 */

@SuppressLint("MissingPermission", "StaticFieldLeak", "HardwareIds")
object DeviceUuidUtil {

    private val PREFS_FILE = "device_id.xml"
    private val PREFS_DEVICE_TYPE = "device_type"
    private val PREFS_DEVICE_ID = "device_id"

    private fun save(context: Context, type: String, value: String) {
        deviceUUID = value
        deviceType = type
        val prefs = context.getSharedPreferences(PREFS_FILE, 0)

        //将取到的内容缓存起来
        prefs?.edit()?.run {
            putString(PREFS_DEVICE_ID, deviceUUID)?.apply()
            putString(PREFS_DEVICE_TYPE, deviceType)?.apply()
        }
    }

    fun getDeviceID(context: Context) {
        var type = ""
        var uuid = ""
        //imei
        if (PermissionUtils.isOpenPermisson(context, Manifest.permission.READ_PHONE_STATE)) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    uuid = (context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager).imei
                    type = "IMEI"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //deviceID
        if (TextUtils.isEmpty(uuid)) {
            try {
                uuid = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                type = "DEVICEID"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //运营商、型号、host、类型等
        if (TextUtils.isEmpty(uuid) || "9774d56d682e549c" == uuid) {
            val BOARD = if (Build.BOARD == null) "" else Build.BOARD
            val BRAND = if (Build.BRAND == null) "" else Build.BRAND
            val CPU_ABI = if (Build.BOARD == null) "" else Build.CPU_ABI
            val DEVICE = if (Build.DEVICE == null) "" else Build.DEVICE
            val DISPLAY = if (Build.DISPLAY == null) "" else Build.DISPLAY
            val HOST = if (Build.HOST == null) "" else Build.HOST
            val ID = if (Build.ID == null) "" else Build.ID
            val MANUFACTURER = if (Build.MANUFACTURER == null) "" else Build.MANUFACTURER
            val MODEL = if (Build.MODEL == null) "" else Build.MODEL
            val PRODUCT = if (Build.PRODUCT == null) "" else Build.PRODUCT
            val TAGS = if (Build.TAGS == null) "" else Build.TAGS
            val TYPE = if (Build.TYPE == null) "" else Build.TYPE
            val USER = if (Build.USER == null) "" else Build.USER

            uuid = "35${BOARD.length % 10}${BRAND.length % 10}${CPU_ABI.length % 10}${DEVICE.length % 10}${DISPLAY.length % 10}" +
                    "${HOST.length % 10}${ID.length % 10}${MANUFACTURER.length % 10}${MODEL.length % 10}${PRODUCT.length % 10}" +
                    "${TAGS.length % 10}${TYPE.length % 10}${USER.length % 10}"

            type = "UUID"
        }

        //如果上面都为空，则生成一个随机码
        uuid = if (TextUtils.isEmpty(uuid)) {
            type = "UUID"
            UUID.randomUUID().toString()
        } else {
            //如果上面都不为空，则将取到的内容通过MD5加密生成
            UUID.nameUUIDFromBytes(uuid.toByteArray()).toString()
        }

        save(context, type, uuid)
    }

}