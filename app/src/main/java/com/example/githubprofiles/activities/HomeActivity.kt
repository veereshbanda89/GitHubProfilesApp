package com.example.githubprofiles.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.githubprofiles.R
import com.example.githubprofiles.fragments.ProfilesListFragment
import com.example.githubprofiles.model.ProfilesResponse
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            when {
                supportFragmentManager.backStackEntryCount >= 1 -> supportFragmentManager.popBackStack()
                else -> finish()
            }
        }
        supportActionBar?.setTitle("")
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer,ProfilesListFragment.newInstance())
            .commit()
    }

    public fun showFragment(
        fragment: Fragment
    ){
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer,fragment)
            .addToBackStack(null)
            .commit()
    }
}
