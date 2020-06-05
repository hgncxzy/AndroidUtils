@file:Suppress("unused")

package com.xzy.utils.context

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Starts the Activity [targetActivity], in a more concise way, while still allowing to configure
 * the [Intent] in the optional [configIntent] lambda.
 */
inline fun <reified targetActivity : Activity> Context.startActivity(
        extras: Bundle? = null,
        configIntent: Intent.() -> Unit = {}
) {
    val intent = Intent(this, targetActivity::class.java)
    if (extras != null) {
        intent.putExtras(extras)
    }
    startActivity(intent.apply(configIntent))
}

/**
 * Starts the Activity that with the passed [packageName] and [activityName], in a more concise way,
 * while still allowing to configure the [Intent] in the optional [configIntent] lambda.
 *
 * If there's no matching [Activity], the underlying platform API will throw an
 * [ActivityNotFoundException].
 */
@Throws(ActivityNotFoundException::class)
inline fun Context.startActivity(
        packageName: String? = null,
        activityName: String,
        extras: Bundle? = null,
        singleTask: Boolean = false,
        configIntent: Intent.() -> Unit = {}
) {
    val intent = Intent()
    val pkgName = packageName ?: getPackageName()
    intent.setClassName(pkgName, activityName)
    if (extras != null) {
        intent.putExtras(extras)
    }
    if (singleTask) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    startActivity(intent.apply(configIntent))
}
