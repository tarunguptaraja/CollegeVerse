package com.tarunguptaraja.collegeverse.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.tarunguptaraja.collegeverse.R
import com.tarunguptaraja.collegeverse.databinding.ActivityHomeBinding
import com.tarunguptaraja.collegeverse.model.Student
import com.tarunguptaraja.collegeverse.model.Teacher
import com.tarunguptaraja.collegeverse.model.User
import com.tarunguptaraja.collegeverse.viewmodel.HomeActivityViewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewBinding: ActivityHomeBinding
    private lateinit var viewModel: HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setSupportActionBar(viewBinding.appBarHome.toolbar)

        viewBinding.appBarHome.fab.setOnClickListener {
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            startActivity(Intent(applicationContext, EventActivity::class.java))
        }
        val drawerLayout: DrawerLayout = viewBinding.drawerLayout
        val navView: NavigationView = viewBinding.navView
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_home) as NavHostFragment
        val navController = navHostFragment.navController

        viewModel = HomeActivityViewModel()

        val sharedPreferences: SharedPreferences =
            applicationContext.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val gson = Gson()
        var json: String? = sharedPreferences.getString("USER", "")
        val user: User = gson.fromJson(json, User::class.java)
        if (user.role == "Student") {
            json = sharedPreferences.getString("STUDENT", "")
            val student: Student = gson.fromJson(json, Student::class.java)
            viewModel.student = student
        } else {
            json = sharedPreferences.getString("TEACHER", "")
            val teacher: Teacher = gson.fromJson(json, Teacher::class.java)
            viewModel.teacher = teacher
        }
        viewModel.user = user

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_request, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (user.role == "Teacher" && viewModel.teacher.isHOD) navView.menu.findItem(R.id.nav_request).isVisible =
            true

        navView.getHeaderView(0).findViewById<TextView>(R.id.username).text = viewModel.user.name
        navView.getHeaderView(0).findViewById<TextView>(R.id.phone_number).text =
            viewModel.user.phoneNumber

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}