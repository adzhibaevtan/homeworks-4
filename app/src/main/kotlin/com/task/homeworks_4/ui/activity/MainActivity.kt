package com.task.homeworks_4.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.task.homeworks_4.R
import com.task.homeworks_4.data.local.preferences.PreferencesManager
import com.task.homeworks_4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController
    }
    private val preferencesManager by lazy {
        PreferencesManager(
            this.getSharedPreferences(
                "homework4.preferences",
                Context.MODE_PRIVATE
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defineStartDestinationDependingOnTheAuthenticationProcess()
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.createNewTaskFragment,
                R.id.navigation_profile,
                R.id.verifyPhoneNumberWithCodeFragment,
                R.id.inputPhoneNumberFragment
            )
        )

        val bottomFragments = arrayListOf(
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications,
            R.id.navigation_profile
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.navView.isVisible = bottomFragments.contains(destination.id)
            if (destination.id == R.id.onBoardingFragment) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }
    }

    private fun defineStartDestinationDependingOnTheAuthenticationProcess() {
        val navGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)

        when (preferencesManager.isAuthenticated) {
            true ->
                navGraph.setStartDestination(R.id.navigation_home)
            false ->
                navGraph.setStartDestination(R.id.onBoardingFragment)
        }
        navController.graph = navGraph
    }
}