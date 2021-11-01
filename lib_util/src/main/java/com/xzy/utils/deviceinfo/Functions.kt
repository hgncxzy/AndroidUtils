package com.xzy.utils.deviceinfo

import android.Manifest.permission
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager

import androidx.annotation.RequiresPermission
import com.xzy.utils.appctx.appCtx
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException

import java.util.LinkedList
import java.util.Locale

@Suppress("unused")
object DeviceUtils {

    /**
     * Return the ip address.
     *
     * Must hold `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @param useIPv4 True to use ipv4, false otherwise.
     * @return the ip address
     */
    @RequiresPermission(permission.INTERNET)
    fun getIPAddress(useIPv4: Boolean): String? {
        try {
            val nis =
                    NetworkInterface.getNetworkInterfaces()
            val adds =
                    LinkedList<InetAddress>()
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement()
                // To prevent phone of xiaomi return "10.0.2.15"
                if (!ni.isUp || ni.isLoopback) continue
                val addresses = ni.inetAddresses
                while (addresses.hasMoreElements()) {
                    adds.addFirst(addresses.nextElement())
                }
            }
            for (add in adds) {
                if (!add.isLoopbackAddress) {
                    val hostAddress = add.hostAddress
                    val isIPv4 = hostAddress.indexOf(':') < 0
                    if (useIPv4) {
                        if (isIPv4) return hostAddress
                    } else {
                        if (!isIPv4) {
                            val index = hostAddress.indexOf('%')
                            return if (index < 0) hostAddress.toUpperCase(Locale.ROOT) else hostAddress.substring(
                                    0,
                                    index
                            ).toUpperCase(Locale.ROOT)
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 判断是否是真机 -- 通过 bt 的有无或者 bt 的名称判断
     * */
    private fun checkIsRealPhone(): Boolean {
        val bt = BluetoothAdapter.getDefaultAdapter()
        return if (bt == null) {
            false
        } else {
            val btName = bt.name
            btName.isNotEmpty()
        }
    }

    /**
     * 判断是否有 SIM 卡，注意获取 android.permission.READ_PHONE_STATE 权限
     * */
    @SuppressLint("MissingPermission", "HardwareIds")
    fun checkHasSimCard(context: Context): Boolean {
        val tm = context.applicationContext
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val simSer = tm.simSerialNumber
        return simSer.isNotEmpty()
    }


    // 是否是模拟器
    val isEmulator: Boolean
        get() {
            val url = "tel:" + "123456"
            val intent = Intent()
            intent.data = Uri.parse(url)
            intent.action = Intent.ACTION_DIAL
            // 是否可以处理跳转到拨号的 Intent
            val canResolveIntent = intent.resolveActivity(appCtx.packageManager) != null
            return (
                    Build.FINGERPRINT.startsWith("generic") ||
                            Build.FINGERPRINT.toLowerCase(Locale.ROOT).contains("vbox") ||
                            Build.FINGERPRINT.toLowerCase(Locale.ROOT).contains("test-keys") ||
                            Build.MODEL.contains("google_sdk") ||
                            Build.MODEL.contains("Emulator") ||
                            Build.SERIAL.equals("unknown", ignoreCase = true) ||
                            Build.SERIAL.equals("android", ignoreCase = true) ||
                            Build.MODEL.contains("Android SDK built for x86") ||
                            Build.MANUFACTURER.contains("Genymotion") ||
                            Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
                            "google_sdk" == Build.PRODUCT ||
                            (appCtx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).networkOperatorName.toLowerCase(Locale.ROOT) == "android" ||
                            !canResolveIntent
                    )
        }


}
