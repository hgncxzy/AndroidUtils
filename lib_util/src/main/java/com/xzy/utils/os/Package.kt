@file:Suppress("unused")
package com.xzy.utils.os

import android.content.ComponentName
import android.content.pm.PackageManager
import com.xzy.utils.appctx.appCtx

/** All installed apps. */
val installedApps: List<AppInfo>
    get() {
        val pm = appCtx.packageManager
        val packages = pm.getInstalledPackages(0)

        val apps = mutableListOf<AppInfo>()
        packages.forEach {
            @Suppress("UNNECESSARY_SAFE_CALL") val name =
                    pm.getApplicationLabel(it.applicationInfo)?.toString() ?: ""
            apps.add(AppInfo(name, it.packageName, it.versionName ?: ""))
        }
        return apps
    }

/** Check app of which package named [pkgName] whether been installed. */
fun isInstalled(pkgName: String): Boolean {
    return try {
        appCtx.packageManager.getPackageInfo(pkgName, 0) != null
    } catch (e: Exception) {
        false
    }
}

/** Check app whether be in main process. */
fun isMainProcess() = appCtx.packageName == processName

/** Check if component [clazz] is enabled. */
fun isComponentEnabled(clazz: Class<*>): Boolean {
    val componentName = ComponentName(appCtx, clazz)
    return appCtx.packageManager.getComponentEnabledSetting(componentName) !=
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
}

/** Check if component [clazz] is disabled. */
fun isComponentDisabled(clazz: Class<*>): Boolean {
    val componentName = ComponentName(appCtx, clazz)
    return appCtx.packageManager.getComponentEnabledSetting(componentName) ==
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
}

/** Enable component. */
fun enableComponent(clazz: Class<*>) {
    setComponentState(clazz, true)
}

/** Disable component. */
fun disableComponent(clazz: Class<*>) {
    setComponentState(clazz, false)
}

private fun setComponentState(clazz: Class<*>, enable: Boolean) {
    val componentName = ComponentName(appCtx, clazz)
    val pm = appCtx.packageManager
    val oldState = pm.getComponentEnabledSetting(componentName)
    val newState = if (enable)
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED
    else
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED
    if (newState != oldState) {
        val flags = PackageManager.DONT_KILL_APP
        pm.setComponentEnabledSetting(componentName, newState, flags)
    }
}

@Suppress("unused")
class AppInfo(val name: String, val pkgName: String, val versionName: String)
