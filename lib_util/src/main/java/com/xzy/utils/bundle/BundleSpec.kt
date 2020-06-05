

package com.xzy.utils.bundle

import android.os.Bundle
import com.xzy.utils.thread.isMainThread

open class BundleSpec {

    @PublishedApi
    internal var currentBundle: Bundle?
        get() = if (isMainThread) currentBundleMainThread else currentBundleByThread.get()
        set(value) {
            if (isMainThread) currentBundleMainThread = value else currentBundleByThread.set(value)
        }

    @PublishedApi
    internal var isReadOnly: Boolean
        get() = if (isMainThread) isReadOnlyMainThread else isReadOnlyByThread.get() ?: false
        set(value) {
            if (isMainThread) isReadOnlyMainThread = value else isReadOnlyByThread.set(value)
        }

    private var currentBundleMainThread: Bundle? = null
    private val currentBundleByThread by lazy { ThreadLocal<Bundle?>() }
    private var isReadOnlyMainThread = false
    private val isReadOnlyByThread by lazy { ThreadLocal<Boolean>() }
}
