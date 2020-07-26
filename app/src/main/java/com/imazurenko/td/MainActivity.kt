package com.imazurenko.td

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.imazurenko.td.databinding.MainActivityBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as TdApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        val binding: MainActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.main_activity)

        setSupportActionBar(findViewById(R.id.MainActivityToolbar))

        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.MainActivityNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navController?.makeActionAndNavigate { this.viewModel.menuItemSelected(item) }
        return true
    }
}

