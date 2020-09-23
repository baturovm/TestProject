package dev.test.project.abstracts

import androidx.annotation.LayoutRes
import dev.test.project.R
import dev.test.project.utils.setVisibility
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatFragment

abstract class BaseFragment(@LayoutRes id: Int): MvpAppCompatFragment(id) {

    fun showBackIcon(value: Boolean) {
        when(value) {
            true -> requireActivity().toolbar_main.setNavigationIcon(R.drawable.back_arrow)
            false -> requireActivity().toolbar_main.navigationIcon = null
        }
    }

    fun showBottomNavigation(value: Boolean) {
        requireActivity().nav_view_main.setVisibility(value)
    }

    override fun onDetach() {
        super.onDetach()
        showBackIcon(false)
        showBottomNavigation(true)
    }
}