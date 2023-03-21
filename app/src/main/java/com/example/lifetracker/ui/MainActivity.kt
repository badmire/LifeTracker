package com.example.lifetracker.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
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
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.material.internal.NavigationMenu

//private fun GoogleSignIn() {
//
//    if (!isUserSignedIn()) {
//        val gso = GoogleSignInOptions
//            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .requestProfile()
//            .requestScopes(Scope(DriveScopes.DRIVE))
//            .build()
//    }
//
//}

class MainActivity : AppCompatActivity() {

    // Container for nav drawer/app bar
    private lateinit var appBarConfig: AppBarConfiguration

    // Containers for GoogleDrive stuff
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

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

        var menu = findViewById<NavigationView>(R.id.nav_view)?.menu
//        val exportButtonItem = menu!!.add("Export Data")
//        exportButtonItem.setOnMenuItemClickListener {
//
//            oneTapClient = Identity.getSignInClient(this)
//            signInRequest = BeginSignInRequest.builder()
//                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
//                    .setSupported(true)
//                    .build())
//                .setGoogleIdTokenRequestOptions(
//                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.your_web_client_id))
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build())
//                // Automatically sign in when exactly one credential is retrieved.
//                .setAutoSelectEnabled(true)
//                .build()
//            // ...
//
//            return@setOnMenuItemClickListener true
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) ||
                super.onSupportNavigateUp()
    }
}