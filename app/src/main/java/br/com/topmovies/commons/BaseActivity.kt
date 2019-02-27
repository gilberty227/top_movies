package br.com.topmovies.commons

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun addFragment(frag: Fragment, @IdRes container: Int, addToBackStack: Boolean) {
        this.addFragment(frag, container, addToBackStack, false)
    }

    protected fun replaceFragment(frag: Fragment, @IdRes container: Int, addToBackStack: Boolean) {
        this.addFragment(frag, container, addToBackStack, true)
    }

    protected fun addFragment(frag: Fragment, @IdRes container: Int, addToBackStack: Boolean, forceReplace: Boolean) {
        val fm = this.supportFragmentManager
        val fragment = fm.findFragmentById(container)
        val ft = fm.beginTransaction()
        if (addToBackStack) {
            ft.addToBackStack(frag.javaClass.canonicalName)
        }

        if (!this.isFinishing && !this.isDestroyed) {
            if ((fragment == null || addToBackStack) && !forceReplace) {
                ft.add(container, frag, frag.javaClass.canonicalName)
                ft.commitAllowingStateLoss()
            } else {
                ft.replace(container, frag, frag.javaClass.canonicalName)
                ft.commitAllowingStateLoss()
            }

        }
    }

    protected fun clearFragmentBackStack() {
        val manager = this.supportFragmentManager
        while (manager.backStackEntryCount > 0) {
            manager.popBackStackImmediate()
        }

    }

    protected fun getCurrentFragment(@IdRes containerId: Int): Fragment? {
        val fm = this.supportFragmentManager
        return fm.findFragmentById(containerId)
    }


}
