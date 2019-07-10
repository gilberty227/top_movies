package br.com.topmovies.commons

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


abstract class BaseFragment : Fragment() {

    protected fun isActivityValid(): Boolean {
        return activity != null && !activity!!.isFinishing
    }

    protected fun clearBackStack() {
        val manager = fragmentManager
        manager?.let {
            while (it.backStackEntryCount > 0)
                it.popBackStackImmediate()
        }
    }

    protected fun switchToFragment(frag: Fragment, containerId: Int, addToBackStack: Boolean) {
        switchToFragment(frag, containerId, null, addToBackStack, false)
    }

    protected fun switchToFragment(frag: Fragment, containerId: Int, bundle: Bundle, addToBackStack: Boolean) {
        switchToFragment(frag, containerId, bundle, addToBackStack, false)
    }

    protected fun switchToFragment(
        frag: Fragment,
        containerId: Int,
        bundle: Bundle?,
        addToBackStack: Boolean,
        replace: Boolean
    ) {
        if (isActivityValid()) {
            val fm = activity!!.supportFragmentManager
            val fragment = fm.findFragmentById(containerId)

            bundle?.let {
                frag.arguments = bundle
            }

            val ft = fm.beginTransaction()



            if (addToBackStack)
                ft.addToBackStack(frag.javaClass.name)

            if (fragment == null || addToBackStack && !replace) {
                ft.add(containerId, frag)
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ft.commitAllowingStateLoss()
            } else {
                ft.replace(containerId, frag)
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ft.commitAllowingStateLoss()
            }
        }
    }

    protected fun setActionBar(toolbar: Toolbar, displayHomeAsUpEnabled: Boolean) {
        if (isBaseActivity()) {
            (activity as BaseActivity).setSupportActionBar(toolbar)
            (activity as BaseActivity).supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
        }
    }

    protected fun setActionBarDisplayTitleEnabled(displayTitle: Boolean) {
        val actionBar = getActionBar()
        actionBar?.setDisplayShowTitleEnabled(displayTitle)
    }

    protected fun setActionBarBackButtonEnabled(displayButton: Boolean) {
        val actionBar = getActionBar()
        actionBar?.let {
            it.setHomeButtonEnabled(displayButton)
            it.setDisplayHomeAsUpEnabled(displayButton)
            it.setDisplayShowHomeEnabled(displayButton)
        }
    }

    protected fun setActionBarVisibility(visibility: Boolean) {
        if (activity != null) {
            val actionBar = (activity as BaseActivity).supportActionBar

            if (actionBar != null) {
                if (visibility)
                    actionBar.show()
                else
                    actionBar.hide()
            }
        }
    }

    protected fun setActionBarTitle(titleResId: Int) {
        setActionBarTitle(getString(titleResId))
    }

    protected fun setActionBarTitle(title: String) {
        val actionBar = getActionBar()
        actionBar?.let {
            it.title = title
        }
    }

    protected fun isBaseActivity(): Boolean {
        return isActivityValid() && activity is BaseActivity
    }

    protected fun getActionBar(): ActionBar? {
        return if (isBaseActivity()) (activity as BaseActivity).supportActionBar else null

    }
}
