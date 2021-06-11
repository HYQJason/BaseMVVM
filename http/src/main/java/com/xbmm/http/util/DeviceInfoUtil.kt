package com.xbmm.http.util

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.hx.ice.base.BasicApplication
import com.xbmm.http.constants.HttpConstants.Risk_Net_Type_Mobile
import com.xbmm.http.constants.HttpConstants.Risk_Net_Type_No
import com.xbmm.http.constants.HttpConstants.Risk_Net_Type_Wifi
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException


/**
 * @ClassName: DeviceInfoUtil
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/12 14:08
 */
class DeviceInfoUtil {

    companion object {

        /**
         * 获取App版本号
         * @param context
         * @return version
         */
        fun getAppVersion(context: Context): String? {
            // PackageManager 包管理器 读取所有程序清单文件信息
            val pm = context.packageManager
            // 根据应用程序的包名 获取到应用程序信息
            return try {
                // getPackageName() 获取当前程序的包名
                val packageInfo = pm.getPackageInfo(context.packageName, 0)
                packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        /**
         * 得到软件显示版本信息
         *
         * @param context 上下文
         * @return 当前版本信息
         */
        fun getVerName(context: Context): String {
            var verName = ""
            try {
                val packageName = context.packageName
                verName = context.packageManager
                    .getPackageInfo(packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return verName
        }


        /*
         * 屏幕宽度
         */
        fun getScreenWidth(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }

        /**
         * 屏幕高度
         */
        fun getScreenHeight(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }


        /**
         * 获取当前手机系统版本号
         *
         * @return  系统版本号
         */
        fun getSystemVersion(): String {
            return android.os.Build.VERSION.RELEASE
        }

        /**
         * 获取手机型号
         *
         * @return  手机型号
         */
        fun getSystemModel(): String {
            return android.os.Build.MODEL
        }

        /**
         * 获取手机厂商
         *
         * @return  手机厂商
         */
        fun getDeviceBrand(): String {
            return android.os.Build.BRAND
        }

        /**
         * 获取ip地址
         */
        fun getIPAddress(): String {
            val info = (BasicApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            if (info != null && info.isConnected) {
                if (info.type == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                    try {
                        val en = NetworkInterface.getNetworkInterfaces()
                        while (en.hasMoreElements()) {
                            val intf = en.nextElement()
                            val enumIpAddr = intf.inetAddresses
                            while (enumIpAddr.hasMoreElements()) {
                                val inetAddress = enumIpAddr.nextElement()
                                if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                    return inetAddress.getHostAddress()
                                }
                            }
                        }
                    } catch (e: SocketException) {
                        e.printStackTrace()
                    }

                } else if (info.type == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                    val wifiManager = BasicApplication.instance.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                    val wifiInfo = wifiManager.connectionInfo
                    return intIP2StringIP(wifiInfo.ipAddress)
                }
            }

            return "127.0.0.0"
        }

        /**
         * 将得到的int类型的IP转换为String类型
         *
         * @param ip
         * @return
         */
        private fun intIP2StringIP(ip: Int): String {
            return (ip and 0xFF).toString() + "." +
                    (ip shr 8 and 0xFF) + "." +
                    (ip shr 16 and 0xFF) + "." +
                    (ip shr 24 and 0xFF)
        }

        /**
         * 获取Mac地址
         * @param context
         * @return
         */
        fun getMacAddressFromIp(context: Context): String {
            var macS = ""
            val buf = StringBuilder()
            try {
                val mac: ByteArray
                val ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getIPAddress()))
                mac = ne.hardwareAddress
                for (b in mac) {
                    buf.append(String.format("%02X:", b))
                }
                if (buf.isNotEmpty()) {
                    buf.deleteCharAt(buf.length - 1)
                }
                macS = buf.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return macS
        }

        /**
         * 获取网络类型
         */
        fun getNetType(): Int {
            val info = (BasicApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            if (info != null && info.isConnected) {
                when (info.type) {
                    //当前使用2G/3G/4G网络
                    ConnectivityManager.TYPE_MOBILE -> {
                        try {
                            val en = NetworkInterface.getNetworkInterfaces()
                            while (en.hasMoreElements()) {
                                val intf = en.nextElement()
                                val enumIpAddr = intf.inetAddresses
                                while (enumIpAddr.hasMoreElements()) {
                                    val inetAddress = enumIpAddr.nextElement()
                                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                        return Risk_Net_Type_Mobile
                                    }
                                }
                            }
                        } catch (e: SocketException) {
                            e.printStackTrace()
                        }
                    }
                    //当前使用无线网络
                    ConnectivityManager.TYPE_WIFI -> return Risk_Net_Type_Wifi
                }
            }
            //当前无网络连接,请在设置中打开网络
            return Risk_Net_Type_No
        }

    }

}