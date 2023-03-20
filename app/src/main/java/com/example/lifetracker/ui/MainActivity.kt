package com.example.lifetracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.navigateUp


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfig: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Feed in layout container for jetbrains
        setContentView(R.layout.activity_main)

//        var toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        // Instantiate nav fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(
                R.id.nav_host_fragment
            ) as NavHostFragment

        // Get hooks for nav controller
        val navController = navHostFragment.navController

        // Define layout for nav drawer
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        // Hook up app bar with nav drawer layout
        appBarConfig = AppBarConfiguration(navController.graph,drawerLayout)

        // Hook up nav controller and appbar/drawer
        setupActionBarWithNavController(navController, appBarConfig)

        // Attach nav view to viewmodel
        findViewById<NavigationView>(R.id.nav_view)?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) ||
                super.onSupportNavigateUp()
    }
}