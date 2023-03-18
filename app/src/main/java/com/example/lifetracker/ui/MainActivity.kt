package com.example.lifetracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lifetracker.data.TaskTemplate
import com.example.myapplication.R
import com.google.android.material.navigation.NavigationView

//TODO: Connect with layouts and nav fragment
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Feed in layout container for jetbrains
        setContentView(R.layout.activity_main)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

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
        return navController.navigateUp(appBarConfig)
                || super.onSupportNavigateUp()
    }
}