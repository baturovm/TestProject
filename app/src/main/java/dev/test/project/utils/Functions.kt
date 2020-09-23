package dev.test.project.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.SparseArray
import android.view.View
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.test.project.R

fun View.setVisibility(value: Boolean) {
    visibility = if(value) View.VISIBLE else View.GONE
}

fun dpFromPx(context: Context, px: Float): Float {
    return px / context.resources.displayMetrics.density
}

fun pxFromDp(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

fun View.toggleActive(): Boolean {
    isActivated = !isActivated
    return isActivated
}