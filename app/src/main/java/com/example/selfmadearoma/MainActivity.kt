package com.example.selfmadearoma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.selfmadearoma.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.aromaInfoFragment,
                R.id.homeViewPagerFragment,
                R.id.aromaCreateFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavView.setupWithNavController(navController)

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.aromaInfoFragment -> {
                    findNavController(R.id.nav_host_fragment)
                        .navigate(R.id.aromaInfoFragment,null, rightAnimOptions)
                    return@setOnItemSelectedListener true
                }
                R.id.homeViewPagerFragment -> {
                    findNavController(R.id.nav_host_fragment)
                        .navigate(R.id.homeViewPagerFragment,null, leftAnimOptions)
                    return@setOnItemSelectedListener true
                }
                R.id.aromaCreateFragment -> {
                    val currentFragment = findNavController(R.id.nav_host_fragment).currentDestination!!
                    if(currentFragment.label == "AromaInfoFragment"){
                        findNavController(R.id.nav_host_fragment)
                            .navigate(R.id.aromaCreateFragment,null, leftAnimOptions)
                        return@setOnItemSelectedListener true
                    }
                    if(currentFragment.label == "HomeViewPagerFragment"){
                        findNavController(R.id.nav_host_fragment)
                            .navigate(R.id.aromaCreateFragment,null, rightAnimOptions)
                        return@setOnItemSelectedListener true
                    }
                }
            }
            false
        }
    }

    companion object {
        val leftAnimOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_left)
            .build()

        val rightAnimOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .build()
    }
}