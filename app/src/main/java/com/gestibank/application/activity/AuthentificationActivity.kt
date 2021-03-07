package com.gestibank.application.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.gestibank.application.R
import com.gestibank.application.fragments.currency.CurrencyAmountFragment
import com.gestibank.application.models.UserModel
import com.google.gson.Gson


class AuthentificationActivity : AppCompatActivity() {
    val EXTRA_MESSAGE = "MESSAGE"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_authentification)


        val btn_sign_in = this.findViewById<Button>(R.id.btn_sign_in_authentification);
        val btn_sign_up = this.findViewById<Button>(R.id.btn_sign_up_authentification);
        val btn_currency_authentification =
            this.findViewById<Button>(R.id.btn_currency_authentification);

        verifyIfConnect()

        btn_currency_authentification.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CurrencyAmountFragment::class.java
                )
            )

        }

        btn_sign_in.setOnClickListener {
            openFragment("signIn")

        }

        btn_sign_up.setOnClickListener {

            openFragment("signUp")


        }


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
                openFragment("CLIENT")
            } else if (user.role == "ADMIN") {
                openFragment("ADMIN")
            }
        }
    }

    /** Called when the user taps the Send button */
    fun openFragment(type: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, type)
        }
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction =
            this.supportFragmentManager?.beginTransaction()!!
        fragmentTransaction.add(R.id.main_nav_host, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commitAllowingStateLoss()
    }

}