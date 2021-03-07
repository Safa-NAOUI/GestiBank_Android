package com.gestibank.application.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.gestibank.application.R
import com.gestibank.application.fragments.users.admin.AdminFragment
import com.gestibank.application.fragments.users.client.ClientFragment
import com.gestibank.application.fragments.sign.SignInFragment
import com.gestibank.application.fragments.sign.SignUpFragment
import com.gestibank.application.models.UserModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

/**
 * SF: https://stackoverflow.com/questions/55990820/how-to-use-navigation-drawer-and-bottom-navigation-simultaneously-navigation-a/
 *
 * Back button press from any fragment takes you to home fragment (Start fragment)
 * of nav graph
 */


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navController = findNavController(R.id.main_nav_host) //Initialising navController


        setSupportActionBar(main_toolbar) //Set toolbar


        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.dashboardFragment,
            R.id.settingsFragment
        )
            .setDrawerLayout(main_drawer_layout)
            .build()
        setSupportActionBar(main_toolbar) //
        supportActionBar?.setIcon(R.drawable.ic_money)

        verifyIfConnect()

    }

    private fun verifyIfConnect() {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return

        val gson = Gson()
        val json: String = sharedPref.getString(getString(R.string.session), "")
        if (json != null && json.isNotEmpty()) {
            val user: UserModel = gson.fromJson(json, UserModel::class.java)
            val UserConnected = resources.getString(R.string.session)
            Log.d("verifyIfConnect", "" + UserConnected)
            if (user.role == "CLIENT") {
                replaceFragment(ClientFragment())
            } else if (user.role == "ADMIN") {
                replaceFragment(AdminFragment())
            }

        } else {
            if (intent != null) {
                val message = intent!!.getStringExtra(EXTRA_MESSAGE)
                if (message != null) {
                    if (message == "signIn") {
                        replaceFragment(SignInFragment())
                    } else if (message == "signUp") {
                        replaceFragment(SignUpFragment())
                    }
                }

            }

        }

    }

    fun openFragment(type: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, type)
        }
        startActivity(intent)
    }

    private fun setupNavControl() {
        main_navigation_view?.setupWithNavController(navController) //Setup Drawer navigation with navController
    }

    fun exitApp() { //To exit the application call this function from fragment
        this.finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean { //Setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        super.onBackPressed() //If drawer is already in closed condition then go back
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction =
            this.supportFragmentManager?.beginTransaction()!!
        fragmentTransaction.add(R.id.main_nav_host, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commitAllowingStateLoss()
    }

}
