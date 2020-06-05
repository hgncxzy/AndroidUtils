@file:Suppress("unused")

package com.xzy.utils.context

import android.content.ComponentName
import android.content.Intent
import com.xzy.utils.appctx.appCtx

/***
 * Android L (lollipop, API 21) introduced a new problem when trying to invoke implicit intent,
 * "java.lang.IllegalArgumentException: Service Intent must be explicit"
 *
 * If you are using an implicit intent, and know only 1 target would answer this intent,
 * This method will help you turn the implicit intent into the explicit form.
 */
fun Intent.toExplicitServiceIntent(): Intent? {
    // Retrieve all services that can match the given intent
    val pm = appCtx.packageManager
    val resolveInfos = pm.queryIntentServices(this, 0)

    if (resolveInfos.size != 1) {
        return null
    }

    // Get component info and create ComponentName
    val resolveInfo = resolveInfos[0]
    val packageName = resolveInfo.serviceInfo.packageName
    val className = resolveInfo.serviceInfo.name
    val component = ComponentName(packageName, className)

    // Create a new intent. Use the old one for extras and such reuse
    val explicitIntent = Intent(this)
    explicitIntent.component = component

    return explicitIntent
}
