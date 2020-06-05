package com.xzy.utils.sp.kotlin

import kotlin.reflect.KProperty

/**
 * A Lazy delegate that keeps a reference to it's initializer and resets it's value if you set it's
 * value with it's current value.
 *
 * Note that this doesn't support nullable values.
 */
class ResettableLazy<T : Any>(private val initializer: () -> T) {

    private var value: T? = null

    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        return value ?: initializer().apply { value = this }
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: T) {
        this.value = value
    }

    fun reset() {
        value = null
    }
}

fun <T : Any> resettableLazy(initializer: () -> T) = ResettableLazy(initializer)
