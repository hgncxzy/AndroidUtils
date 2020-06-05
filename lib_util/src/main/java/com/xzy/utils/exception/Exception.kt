@file:Suppress("unused")

package com.xzy.utils.exception

import android.util.Log

/** Throws an [IllegalStateException] with a message that includes [value]. */
fun unexpectedValue(value: Any?): Nothing = throw IllegalStateException("Unexpected value: $value.")

/** Throws an [IllegalArgumentException] with the passed [argument] and [errorMsg]. */
fun illegalArg(argument: Any?, errorMsg: String? = null): Nothing =
        throw IllegalArgumentException(
                "Illegal argument: $argument${if (errorMsg == null) "." else ", $errorMsg."}"
        )

/** Throws an [UnsupportedOperationException] with the given message. */
fun unsupported(errorMsg: String? = null): Nothing = throw UnsupportedOperationException(errorMsg)

/** Get non null message from throwable. */
val Throwable.msg: String
    get() = message ?: javaClass.simpleName

/** Get stack trace message from throwable. */
val Throwable.stackTraceMsg: String
    get() = Log.getStackTraceString(this)
