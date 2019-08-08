package com.xzy.test.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xzy.utils.app.AppUtils
import com.xzy.utils.path.PathUtils
import kotlinx.android.synthetic.main.activity_app_utils.*
import android.Manifest.permission.REQUEST_INSTALL_PACKAGES
import androidx.core.app.ActivityCompat
import android.os.Build
import android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import androidx.annotation.RequiresApi
import com.xzy.utils.common.Utils
import com.xzy.utils.toast.T
import java.io.File


/**
 * AppUtils 工具测试类，请结合测试用例 AppUtilsTest 一起查看。
 * @author xzy
 * **/
class AppUtilsActivity : AppCompatActivity(), Utils.OnAppStatusChangedListener {
    override fun onForeground() {
        T.showShort(this, "app onForeground")
    }

    override fun onBackground() {
        T.showShort(this, "app onBackground")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.xzy.test.R.layout.activity_app_utils)
        // openApp
        openApp.setOnClickListener {
            AppUtils.openApp(this, AppUtils.getAppPackageName())
        }
        // installAPK
        installAPK.setOnClickListener {
            Per.isGrantExternalRW(this@AppUtilsActivity)
            checkIsAndroidO()
        }

        // 监听 object 的状态 （onForeground or onBackground）
        AppUtils.registerAppStatusChangedListener(this, this)

        // unInstallAPK
        unInstallAPK.setOnClickListener {
            AppUtils.uninstallApp("com.xzy.utils")
        }

        // unInstallAPK2
        unInstallAPK2.setOnClickListener {
            AppUtils.uninstallApp(this, "com.xzy.utils", 3)
        }

        // launchApp
        launchApp.setOnClickListener {
            AppUtils.launchAppDetailsSettings()
        }

        //exitApp
        exitApp.setOnClickListener {
            AppUtils.exitApp()
        }
        // getApkInfo
        getApkInfo.setOnClickListener {
            AppUtils.getApkInfo(PathUtils.getDataPath() + "/test.apk")
        }
        // getApkInfo2
        getApkInfo2.setOnClickListener {
            val path = PathUtils.getDataPath() + "/test.apk"
            if (File(path).exists()) {
                AppUtils.getApkInfo(File(PathUtils.getDataPath() + "/test.apk"))
            } else {
                T.showShort(this, "file not exist")
            }
        }

    }


    /**
     * 判断是否是8.0,8.0需要处理未知应用来源权限问题,否则直接安装
     */
    private fun checkIsAndroidO() {
        if (Build.VERSION.SDK_INT >= 26) {
            val b = packageManager.canRequestPackageInstalls()
            if (b) {
                //安装应用的逻辑(写自己的就可以)
                AppUtils.installAPK( this@AppUtilsActivity, Environment.getExternalStorageDirectory().absolutePath + "/test.apk")
            } else {
                //请求安装未知应用来源的权限
                ActivityCompat.requestPermissions(this,
                        arrayOf(REQUEST_INSTALL_PACKAGES), 1)
            }
        } else {
            //安装应用的逻辑(写自己的就可以)
            AppUtils.installAPK( this@AppUtilsActivity, Environment.getExternalStorageDirectory().absolutePath + "/test.apk")
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                //安装应用的逻辑(写自己的就可以)
                AppUtils.installAPK( this@AppUtilsActivity, Environment.getExternalStorageDirectory().absolutePath + "/test.apk")
            } else {
                val intent = Intent(ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                startActivityForResult(intent, 2)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            2 -> checkIsAndroidO()
            3 -> {
                T.showShort(this, "" + resultCode)
            }
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppUtils.unregisterAppStatusChangedListener(this)
    }
}
