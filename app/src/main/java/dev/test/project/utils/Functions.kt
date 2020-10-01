package dev.test.project.utils

import android.content.Context
import android.view.View

fun View.setVisibility(value: Boolean) {
    visibility = if(value) View.VISIBLE else View.GONE
}

fun pxFromDp(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

fun View.toggleActive(): Boolean {
    isActivated = !isActivated
    return isActivated
}