package com.example.resepku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.resepku.databinding.ActivityDashboardBinding
import com.example.resepku.fragment.FavoriteFragment
import com.example.resepku.fragment.RecipeFragment

class DashboardActivity : AppCompatActivity() {
    private val recipeFragment = RecipeFragment()
    private val favoriteFragment = FavoriteFragment()
    private lateinit var binding : ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(recipeFragment)
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.recipes_Fragment -> replaceFragment(recipeFragment)
                R.id.favorites_Fragment -> replaceFragment(favoriteFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
