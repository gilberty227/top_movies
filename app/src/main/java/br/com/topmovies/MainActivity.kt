package br.com.topmovies

import android.os.Bundle
import br.com.topmovies.commons.BaseActivity
import br.com.topmovies.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(HomeFragment(), R.id.fragment_container, false)
    }

    override fun onResume() {
        super.onResume()
        startToolbar()
    }

    private fun startToolbar() {
        setSupportActionBar(toolbarAccess)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(false)
    }
}
