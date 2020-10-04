package dev.test.project.abstracts

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import dev.test.project.utils.setVisibility
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatFragment

/**
 * Базовый фрагмент
 * - Работа с Toolbar
 * - Управление BottomNavigationView
 */
abstract class BaseFragment(@LayoutRes layoutId: Int): MvpAppCompatFragment(layoutId) {

    /**
     * Управление кнопкой "назад" в Toolbar
     * @param value Показать(true), скрыть(false)
     */
    fun showBackIcon(value: Boolean) {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(value)
    }

    /**
     * Управление видимостью Bottom Navigation View
     * @param value Показать(true), скрыть(false)
     */
    fun showBottomNavigation(value: Boolean) {
        requireActivity().nav_view_main.setVisibility(value)
    }

    /**
     * Навигация к указанной вкладке Bottom Navigation View
     * @param id идентификатор пункта
     */
    fun setSelectedTab(@IdRes id: Int) {
        requireActivity().nav_view_main.selectedItemId = id
    }

    /**
     * Установка заголовка Toolbar
     * @param title заголовок
     */
    fun setTitleToolbar(title: String) {
        requireActivity().toolbar_title.text = title
    }

    /**
     * Сброс значений
     */
    override fun onDestroy() {
        super.onDestroy()
        showBackIcon(false)
        showBottomNavigation(true)
    }
}