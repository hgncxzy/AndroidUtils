package com.xzy.test.app


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xzy.utils.path.PathUtils
import kotlinx.android.synthetic.main.activity_app_utils.*
import android.os.Environment

import com.xzy.utils.app.AppUtils.*
import com.xzy.utils.common.Utils
import com.xzy.utils.toast.ToastUtils.showShort
import java.io.File


/**
 * AppUtils 工具测试类，请结合测试用例 AppUtilsTest 一起查看。
 * @author xzy
 * **/
@Suppress("unused")
class AppUtilsActivity : AppCompatActivity(), Utils.OnAppStatusChangedListener {
    override fun onForeground() {
        showShort(this, "app onForeground")
    }

    override fun onBackground() {
        showShort(this, "app onBackground")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.xzy.test.R.layout.activity_app_utils)
        //动态申请权限
        // openApp
        openApp.setOnClickListener {
            openApp(this, getAppPackageName())
        }
        // installAPK
        // 需要先动态申请读写权限
        installAPK.setOnClickListener {
            @Suppress("DEPRECATION")
            installApp(Environment.getExternalStorageDirectory().absolutePath + "/test.apk")
        }

        // 监听 object 的状态 （onForeground or onBackground）
        registerAppStatusChangedListener(this, this)

        // unInstallAPK
        unInstallAPK.setOnClickListener {
            uninstallApp("com.xzy.utils")
        }

        // unInstallAPK2
        unInstallAPK2.setOnClickListener {
            uninstallApp(this, "com.xzy.utils", UNINSTALL_APK)
        }

        // launchApp
        launchApp.setOnClickListener {
            launchAppDetailsSettings()
        }

        //exitApp
        exitApp.setOnClickListener {
            exitApp()
        }
        // getApkInfo
        getApkInfo.setOnClickListener {
            getApkInfo(PathUtils.getDataPath() + "/test.apk")
        }
        // getApkInfo2
        getApkInfo2.setOnClickListener {
            val path = PathUtils.getDataPath() + "/test.apk"
            if (File(path).exists()) {
                getApkInfo(File(PathUtils.getDataPath() + "/test.apk"))
            } else {
                showShort(this, "file not exist")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterAppStatusChangedListener(this)
    }
}
