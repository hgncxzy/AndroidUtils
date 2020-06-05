@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.xzy.utils.resource

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.xzy.utils.appctx.appCtx

@SuppressLint("NewApi")
@ColorInt
fun Context.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

inline fun Fragment.color(@ColorRes colorRes: Int) = requireContext().color(colorRes)

inline fun View.color(@ColorRes colorRes: Int) = context.color(colorRes)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appColor(@ColorRes colorRes: Int) = appCtx.color(colorRes)

fun Context.colorSL(@ColorRes colorRes: Int): ColorStateList? =
        ResourcesCompat.getColorStateList(resources, colorRes, theme)

inline fun Fragment.colorSL(@ColorRes colorRes: Int) = requireContext().colorSL(colorRes)

inline fun View.colorSL(@ColorRes colorRes: Int) = context.colorSL(colorRes)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appColorSL(@ColorRes colorRes: Int) = appCtx.colorSL(colorRes)

// Styled resources below

private inline val defaultColor get() = Color.RED

@ColorInt
fun Context.styledColor(@AttrRes attr: Int): Int =
        withStyledAttributes(attr) { getColor(it, defaultColor) }

inline fun Fragment.styledColor(@AttrRes attr: Int) = requireContext().styledColor(attr)

inline fun View.styledColor(@AttrRes attr: Int) = context.styledColor(attr)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledColor(@AttrRes attr: Int) = appCtx.styledColor(attr)

fun Context.styledColorSL(@AttrRes attr: Int): ColorStateList? =
        withStyledAttributes(attr) { getColorStateList(it) }

inline fun Fragment.styledColorSL(@AttrRes attr: Int) = context!!.styledColorSL(attr)

inline fun View.styledColorSL(@AttrRes attr: Int) = context.styledColorSL(attr)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledColorSL(@AttrRes attr: Int) = appCtx.styledColorSL(attr)
