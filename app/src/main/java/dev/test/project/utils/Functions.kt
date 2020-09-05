package dev.test.project.utils

import android.content.Context
import android.view.View

fun View.setVisibility(value: Boolean) {
    visibility = if(value) View.VISIBLE else View.GONE
}

fun dpFromPx(context: Context, px: Float): Float {
    return px / context.resources.displayMetrics.density
}

fun pxFromDp(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}