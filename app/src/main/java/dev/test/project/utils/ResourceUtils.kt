package dev.test.project.utils

import androidx.annotation.StringRes
import dev.test.project.App

object ResourceUtils {
    fun getString(@StringRes resId: Int): String {
        return App.getContext().getString(resId)
    }
}