package io.alup.insurance

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.alup.insurance.databinding.ActivityAlupBinding
import io.alup.insurance.model.Util.Companion.visible
import io.alup.insurance.viewModel.AuthViewModel
import io.alup.insurance.viewModel.HomeViewModel

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * The main activity of the app, hosts the navigation graph and all fragments
 * @see AppCompatActivity
 */
@AndroidEntryPoint
class AlupActivity : AppCompatActivity() {
    private lateinit var b: ActivityAlupBinding
    lateinit var controller: NavController
    lateinit var app: AlupApplication
    val authViewModel: AuthViewModel by viewModels()
    val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAlupBinding.inflate(layoutInflater)
        setContentView(b.root)
        controller = findNavController(R.id.nav_host_fragment_activity_main)
        setSupportActionBar(b.toolbar)
        setupActionBarWithNavController(controller, AppBarConfiguration(setOf(R.id.homeFragment)))
        b.navView.setupWithNavController(controller)
        b.toolbar.setNavigationIcon(R.drawable.arrow_left)
    }

    override fun onResume() {
        super.onResume()
        app = application as AlupApplication
        homeViewModel.ui.observe(this) {
            b.navView.visible(it.showBottomNav)
            b.appBar.visible(it.showToolbar)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (controller.currentDestination?.id == R.id.homeFragment) finish()
                else controller.navigateUp()
            }
        })
    }
}