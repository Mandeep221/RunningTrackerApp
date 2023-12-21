package com.msarangal.runningtracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.msarangal.runningtracker.R
import com.msarangal.runningtracker.db.RunDao
import com.msarangal.runningtracker.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.msarangal.runningtracker.ui.theme.RunningTrackerTheme
import com.msarangal.runningtracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var runDao: RunDao

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        setupActionBar()

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.settingsFragment || destination.id == R.id.runFragment || destination.id == R.id.statisticsFragment) {
                bottomNavigationView.visibility = View.VISIBLE
            } else if (destination.id == R.id.trackingFragment || destination.id == R.id.setupFragment) {
                bottomNavigationView.visibility = View.GONE
            }
        }
        // In case when the user clicks on the notification and the MainActivity is destroyed
        // MainActivity will be re-created, but since we need to launch the tracking fragment,
        // therefore we need this method here inside onCreate when the activity is re-crated.
        navigateToTrackingFragmentIfNeeded(intent)
    }

    // If activity was not destroyed and the notification was clicked
    // the pending intent will be received here
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = "Running App by Mandeep"
        }
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            navHostFragment.findNavController().navigate(R.id.action_global_trackingFragment)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    RunningTrackerTheme {
        Greeting("Android")
    }
}