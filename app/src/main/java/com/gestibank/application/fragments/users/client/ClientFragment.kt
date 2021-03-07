package com.gestibank.application.fragments.users.client

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.gestibank.application.R
import com.gestibank.application.activity.AuthentificationActivity
import com.gestibank.application.activity.MainActivity
import com.gestibank.application.fragments.currency.CurrencyAmountFragment
import com.gestibank.application.models.UserModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_client.*


class ClientFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_client, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_currency_authentification.setOnClickListener {
            replaceFragment(CurrencyAmountFragment())
        }

        btn_sign_desconnect.setOnClickListener {
            val sharedPref =
                activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
            with(sharedPref.edit()) {
                putString(getString(R.string.session), null).apply()
            }

            val count = activity?.supportFragmentManager?.backStackEntryCount

            for (i in 0 until count!!) {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction =
            activity?.supportFragmentManager?.beginTransaction()!!
        fragmentTransaction.add(R.id.main_nav_host, fragment)
        fragmentTransaction.addToBackStack("add")
        fragmentTransaction.commitAllowingStateLoss()
    }

}



