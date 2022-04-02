package com.example.artcollectionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.artcollectionapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bottomNavigation = binding.bottomNavigation

        val navController = findNavController(R.id.fragmentContainer)

        bottomNavigation.setupWithNavController(navController)

        if(supportActionBar != null){
            supportActionBar?.hide()
        }

        navController.addOnDestinationChangedListener{_, nc: NavDestination, _->
            if(nc.id == R.id.DepartmentFragment ||
                nc.id == R.id.SearchFragment || nc.id == R.id.MainFragment){
                bottomNavigation.visibility = View.VISIBLE
            }else{
                bottomNavigation.visibility = View.GONE
            }
        }
    }
}