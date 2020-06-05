package com.xzy.utils.initprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.net.Uri
import com.xzy.utils.exception.unexpectedValue
import com.xzy.utils.exception.unsupported

/** Base class for [ContentProvider]s used for initialization purposes. */
abstract class InitProvider : ContentProvider() {

    private val emptyApplicationIdProviderAuthority = "com.xzy.util.initprovider"

    override fun attachInfo(context: Context?, info: ProviderInfo?) {
        checkContentProviderAuthority(info)
        super.attachInfo(context, info)
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? = unsupported()

    override fun query(
            p0: Uri,
            p1: Array<String>?,
            p2: String?,
            p3: Array<String>?,
            p4: String?
    ): Cursor? = unsupported()

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<String>?): Int =
            unsupported()

    override fun delete(p0: Uri, p1: String?, p2: Array<String>?): Int = unsupported()

    override fun getType(p0: Uri): String? = unsupported()

    private fun checkContentProviderAuthority(info: ProviderInfo?) {
        checkNotNull(info)
        if (emptyApplicationIdProviderAuthority == info.authority) {
            unexpectedValue(
                    "Incorrect provider authority in manifest. Most likely due to a missing " +
                            "applicationId variable in application's build.gradle."
            )
        }
    }
}
