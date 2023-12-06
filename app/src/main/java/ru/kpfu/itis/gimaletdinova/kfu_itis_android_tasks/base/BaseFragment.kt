package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R

abstract class BaseFragment(@LayoutRes layout: Int): Fragment(layout) {
    open fun getToolbar(): Toolbar? = view?.findViewById(R.id.toolbar)
    protected fun setToolbarTitle(@StringRes titleRes: Int) {
        getToolbar()?.setTitle(titleRes)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(getToolbar() != null)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}