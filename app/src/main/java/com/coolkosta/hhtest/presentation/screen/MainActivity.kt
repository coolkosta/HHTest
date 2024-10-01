package com.coolkosta.hhtest.presentation.screen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.coolkosta.core.util.VacancyFlow
import com.coolkosta.favorite.presentation.screen.FavoriteFragment
import com.coolkosta.feedback.presentation.FeedbackFragment
import com.coolkosta.hhtest.R
import com.coolkosta.hhtest.databinding.ActivityMainBinding
import com.coolkosta.message.MessageFragment
import com.coolkosta.profile.ProfileFragment
import com.coolkosta.search.presentation.screen.searchFragment.SearchFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNav = binding.bottomNavigationView
        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.search
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment()).commit()
        }

        bottomNav.setOnItemSelectedListener { item ->

            val fragment = when (item.itemId) {
                R.id.search -> SearchFragment()
                R.id.favorite -> FavoriteFragment()
                R.id.feedback -> FeedbackFragment()
                R.id.message -> MessageFragment()
                R.id.profile -> ProfileFragment()
                else -> null
            }
            fragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, it)
                    .commit()
            }
            true
        }
        getFavoriteCountBadge()
    }

    private fun getFavoriteCountBadge() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                VacancyFlow.getFavoriteVacancyCount().collect {
                    updateFavoriteCountBadge(it)
                }
            }
        }
    }

    private fun updateFavoriteCountBadge(favoriteCount: Int) {
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.favorite)
        badge.number = favoriteCount
        badge.isVisible = favoriteCount > 0
    }
}