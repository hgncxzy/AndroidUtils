@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.xzy.utils.resource

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.xzy.utils.appctx.appCtx

fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? =
        ResourcesCompat.getDrawable(resources, drawableResId, theme)

inline fun Fragment.drawable(@DrawableRes drawableResId: Int) =
        requireContext().drawable(drawableResId)

inline fun View.drawable(@DrawableRes drawableResId: Int) = context.drawable(drawableResId)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appDrawable(@DrawableRes drawableResId: Int) = appCtx.drawable(drawableResId)

// Styled resources below

fun Context.styledDrawable(@AttrRes attr: Int): Drawable? =
        withStyledAttributes(attr) { getDrawable(it) }

inline fun Fragment.styledDrawable(@AttrRes attr: Int) = context!!.styledDrawable(attr)

inline fun View.styledDrawable(@AttrRes attr: Int) = context.styledDrawable(attr)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledDrawable(@AttrRes attr: Int) = appCtx.styledDrawable(attr)
