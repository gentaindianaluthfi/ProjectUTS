package id.ac.polbeng.gentaindianaluthfi.projectuts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.ac.polbeng.gentaindianaluthfi.projectuts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            loadFragment(ProductListFragment())
            supportActionBar?.title = getString(R.string.title_products)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_products -> {
                    loadFragment(ProductListFragment())
                    supportActionBar?.title = getString(R.string.title_products)
                    true
                }
                R.id.navigation_profile -> {
                    loadFragment(ProfileFragment())
                    supportActionBar?.title = getString(R.string.title_profile)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
