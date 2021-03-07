package com.gestibank.application.fragments.sign


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

import com.gestibank.application.R
import com.gestibank.application.api.APIService
import com.gestibank.application.fragments.users.admin.AdminFragment
import com.gestibank.application.fragments.users.client.ClientFragment
import com.gestibank.application.models.UserModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * AccountsFragment
 */

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_sign_up.setOnClickListener {

            var userModel = UserModel(
                edit_prenom.text.toString(),
                edit_email.text.toString(),
                edit_phone.text.toString(),
                "CLIENT",
                edit_name.text.toString(),
                edit_pwd.text.toString(),
                "",
                "1000"
            )
            SignUp(userModel)

        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction =
            activity?.supportFragmentManager?.beginTransaction()!!
        fragmentTransaction.add(R.id.main_nav_host, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commitAllowingStateLoss()
    }


    private fun SignUp(
        userModel: UserModel
    ) {
        val getClientService = APIService.getClientService()

        val call: Call<UserModel> = getClientService!!.signUpClient(userModel)

        call.enqueue(object : Callback<UserModel?> {
            override fun onResponse(call: Call<UserModel?>?, response: Response<UserModel?>) {

                if (response!!.body() != null) {
                    if (response.isSuccessful) {

                        val sharedPref =
                            activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                        val gson = Gson()
                        val jsonUser = gson.toJson(userModel)
                        with(sharedPref.edit()) {
                            putString(getString(R.string.session), jsonUser).apply()
                        }



                        if (userModel.role == "CLIENT") {
                            replaceFragment(ClientFragment())
                        } else if (userModel.role == "ADMIN") {
                            replaceFragment(AdminFragment())

                        }
                    }
                }
            }

            override fun onFailure(call: Call<UserModel?>, t: Throwable) {

                Toast.makeText(activity, "Email or Password incorrect", Toast.LENGTH_SHORT).show()
            }
        })

    }


}

