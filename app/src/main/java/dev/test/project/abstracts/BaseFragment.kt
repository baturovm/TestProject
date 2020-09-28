package dev.test.project.abstracts

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import dev.test.project.R
import dev.test.project.utils.setVisibility
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatFragment

abstract class BaseFragment(@LayoutRes layoutId: Int): MvpAppCompatFragment(layoutId) {

    fun showBackIcon(value: Boolean) {
        when(value) {
            true -> requireActivity().toolbar_main.setNavigationIcon(R.drawable.back_arrow)
            false -> requireActivity().toolbar_main.navigationIcon = null
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        showBackIcon(false)
        showBottomNavigation(true)
    }
}