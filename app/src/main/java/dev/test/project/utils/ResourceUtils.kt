package dev.test.project.utils

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import dev.test.project.App

object ResourceUtils {
    fun getString(@StringRes resId: Int): String {
        return App.getContext().getString(resId)
    }

    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(App.getContext(), resId)
    }

    fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(App.getContext(), resId)
    }
}