package com.csci5708.maylborrow

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.csci5708.maylborrow.databinding.ActivityMainBinding
import com.csci5708.maylborrow.ui.dashboard.DashboardFragment
import com.csci5708.maylborrow.ui.home.HomeFragment
import com.csci5708.maylborrow.ui.requestUserDetail.fragment.RequestDetail
import com.csci5708.maylborrow.ui.userprofile.UserProfile
import com.csci5708.maylborrow.ui.wishlist.WishlistFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var topBarConfiguration: AppBarConfiguration
    private lateinit var homeFragment: HomeFragment
    private lateinit var dashboardFragment: DashboardFragment
    private lateinit var wishlistFragment: WishlistFragment
    private lateinit var requestDetail: RequestDetail
    private lateinit var userProfile: UserProfile

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(topBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        topBarConfiguration = AppBarConfiguration(navController.graph)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_profile,R.id.navigation_home, R.id.navigation_dashboard, R.id.navigationRequestDetail,R.id.navigation_wishlist))
        
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.navView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.navigation_profile-> {
                    navController.navigate(R.id.navigation_profile)
                }

                R.id.navigation_dashboard-> {
                    navController.navigate(R.id.navigation_dashboard)
                }

                R.id.navigation_home-> {
                    navController.navigate(R.id.navigation_home)
                }

                R.id.navigationRequestDetail-> {
                    navController.navigate(R.id.navigationRequestDetail)
                }

                R.id.navigation_wishlist-> {
                    navController.navigate(R.id.navigation_wishlist)
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}