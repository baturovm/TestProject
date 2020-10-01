package dev.test.project.abstracts

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import dev.test.project.utils.setVisibility
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatFragment

abstract class BaseFragment(@LayoutRes layoutId: Int): MvpAppCompatFragment(layoutId) {

    fun showBackIcon(value: Boolean) {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(value)
    }

    fun showBottomNavigation(value: Boolean) {
        requireActivity().nav_view_main.setVisibility(value)
    }

    fun setSelectedTab(@IdRes id: Int) {
        requireActivity().nav_view_main.selectedItemId = id
    }

    fun setTitleToolbar(title: String) {
        requireActivity().toolbar_title.text = title
    }

    override fun onDestroy() {
        super.onDestroy()
        showBackIcon(false)
        showBottomNavigation(true)
    }
}