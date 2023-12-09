package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments.ProfileFragment

abstract class BaseFragment(@LayoutRes layout: Int): Fragment(layout) {
    open fun getToolbar(): Toolbar? = view?.findViewById(R.id.toolbar)
    protected fun setToolbarTitle(@StringRes titleRes: Int) {
        getToolbar()?.setTitle(titleRes)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(getToolbar() != null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu);
    }
}