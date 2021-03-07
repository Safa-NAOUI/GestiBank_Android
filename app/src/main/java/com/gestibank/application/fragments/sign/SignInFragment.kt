package com.gestibank.application.fragments.sign


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.gestibank.application.R
import com.gestibank.application.api.APIService
import com.gestibank.application.models.UserModel
import com.gestibank.application.fragments.users.admin.AdminFragment
import com.gestibank.application.fragments.users.client.ClientFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_signin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * AccountsFragment
 */

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_sign_in_login.setOnClickListener {
            if (edit_email_cnx.text != null && edit_pwd_cnx.text != null && edit_email_cnx.text.isNotEmpty() && edit_pwd_cnx.text.isNotEmpty()) {
                getUser(edit_email_cnx.text.toString(), edit_pwd_cnx.text.toString())
            } else {
                Toast.makeText(activity, "Email or Password empty", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun getUser(email: String, pwd: String) {
        val getClientService = APIService.getClientService()

        val call: Call<UserModel> = getClientService!!.getUserByEmail(email)

        call.enqueue(object : Callback<UserModel?> {
            override fun onResponse(
                call: Call<UserModel?>?,
                response: Response<UserModel?>
            ) {

                if (response!!.body() != null) {
                    if (response.isSuccessful) {
                        val user: UserModel = response.body()!!
                        if (user.email == email) {
                            if (user.paswwsord == pwd) {


                                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                                val gson = Gson()
                                val jsonUser = gson.toJson(user)
                                with(sharedPref.edit()) {
                                    putString(getString(R.string.session), jsonUser).apply()
                                }


                                if (user.role == "CLIENT") {
                                    addFragmentToFragment(ClientFragment())
                                } else if (user.role == "ADMIN") {
                                    addFragmentToFragment(AdminFragment())
                                }

                            } else {
                                Toast.makeText(
                                    activity,
                                    "Email or Password incorrect",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                activity,
                                "Email or Password incorrect",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<UserModel?>, t: Throwable) {

                Toast.makeText(activity, "Email or Password incorrect", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun addFragmentToFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction =
            activity?.supportFragmentManager?.beginTransaction()!!
        fragmentTransaction.add(R.id.main_nav_host, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
