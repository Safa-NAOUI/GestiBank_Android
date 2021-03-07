package com.gestibank.application.fragments.home


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gestibank.application.activity.MainActivity

import com.gestibank.application.R
import com.gestibank.application.models.UserModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_authentification.*

/**
 * HomeFragment
 *  2 Buttons
 *  Nav graph start fragment
 */

class HomeFragment : Fragment(), View.OnClickListener { //OnClickListener

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_authentification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController() //Initialising navController


        //Initialising button click event listener
        btn_sign_in_authentification.setOnClickListener(this)
        btn_currency_authentification.setOnClickListener(this)
        btn_sign_up_authentification.setOnClickListener(this)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

       // verifyIfConnect()
    }



    private fun verifyIfConnect() {
        val sharedPref = activity!!.getPreferences(Context.MODE_PRIVATE) ?: return

        val gson = Gson()
        val json: String = sharedPref.getString(getString(R.string.session), "")
        if (json != null && json.isNotEmpty()) {
            val user: UserModel = gson.fromJson(json, UserModel::class.java)
            val UserConnected = resources.getString(R.string.session)
        }

    }

    override fun onClick(v: View?) { //When click occurs this function is triggered
        when (v!!.id) { //Check for the id of the view i which click event happened
            //   R.id.home_next_frag_btn -> goToNextFragment()
            R.id.btn_sign_in_authentification -> goToNextFragment("SignIn")
            R.id.btn_sign_up_authentification -> goToNextFragment("SignUP")
            R.id.btn_currency_authentification -> goToNextFragment("CurrencyAmount")
        }
    }

    private fun goToNextFragment(type: String) {
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (type == "SignIn") {
            val action =
                HomeFragmentDirection.actionHomeFragmentToSignInFragment()
            navController.navigate(action)
        } else if (type == "SignUP") {
            val action =
                HomeFragmentDirection.actionHomeFragmentToSignUpFragment()
            navController.navigate(action)
        } else {
            val action =
                HomeFragmentDirection.actionHomeFragmentToCurrencyAmountFragment()
            navController.navigate(action)
        }


    }

    private fun closeApp() {
        (activity as MainActivity).exitApp()
    }

}
