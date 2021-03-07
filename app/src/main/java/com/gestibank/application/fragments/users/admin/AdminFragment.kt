package com.gestibank.application.fragments.users.admin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gestibank.application.R
import com.gestibank.application.adapter.ListAdapterAgent
import com.gestibank.application.adapter.ListAdapterClient
import com.gestibank.application.api.APIService
import com.gestibank.application.api.ClientService
import com.gestibank.application.fragments.users.admin.add.AddAgentFragment
import com.gestibank.application.models.UserModel
import com.gestibank.application.models.UserModelAgent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_client.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminFragment : Fragment() {

    var btn_rquest: Button? = null
    var list_user: RecyclerView? = null
    var list_clients: RecyclerView? = null
    var listClient: ArrayList<UserModel>? = null
    val type = "admin"
    var clientService: ClientService? = null
    var listAgent: ArrayList<UserModelAgent>? = null

    lateinit var recycler_list: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        clientService = APIService.getClientService()

        getAgentValid()
        list_user = activity?.findViewById(R.id.list_user);
        list_user!!.layoutManager = LinearLayoutManager(activity)
        btn_rquest = activity?.findViewById(R.id.btn_rquest);
        btn_rquest?.setOnClickListener {
            showAlertDialogButtonClicked(activity)

        }

        val fab: FloatingActionButton? = activity?.findViewById<FloatingActionButton>(R.id.fab_add)
        fab?.setOnClickListener { view ->
            replaceFragment(AddAgentFragment())
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

    fun showAlertDialogButtonClicked(activity: FragmentActivity?) {

        // Create an alert builder
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
        //   builder.setTitle("List of clients")

        val customLayout: View = layoutInflater
            .inflate(R.layout.dialog_request, null)
        builder.setView(customLayout)

        list_clients = customLayout
            .findViewById(
                R.id.list_request_user
            )

        getClientsEnAttentes()
        val dialog: android.app.AlertDialog? = builder.create()
        dialog!!.show()
    }

    private fun addFragmentToFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction =
            activity?.supportFragmentManager?.beginTransaction()!!
        fragmentTransaction.add(R.id.main_nav_host, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction =
            activity?.supportFragmentManager?.beginTransaction()!!
        fragmentTransaction.add(R.id.main_nav_host, fragment)
        fragmentTransaction.addToBackStack("add")
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onResume() {
        super.onResume()
        getAgentValid()


    }

    fun getClientsEnAttentes() {

        val call: Call<ArrayList<UserModel>> = clientService!!.getClientsEnAttentes()
        call.enqueue(object : Callback<ArrayList<UserModel>> {
            override fun onResponse(
                call: Call<ArrayList<UserModel>>,
                response: Response<ArrayList<UserModel>>
            ) {
                if (response.isSuccessful) {

                    listClient = response.body()

                    list_clients!!.layoutManager = LinearLayoutManager(activity)

                    list_clients!!.adapter = activity?.let {
                        listClient?.let { it1
                            ->
                            ListAdapterClient(it1, activity!!, listAgent)
                        }
                    }


                }
            }

            override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                Log.e("ERROR: ", t.message!!)
            }
        })
    }

    fun getAgentValid() {

        val call: Call<ArrayList<UserModelAgent>> = clientService!!.getListAgents()
        call.enqueue(object : Callback<ArrayList<UserModelAgent>> {
            override fun onResponse(
                call: Call<ArrayList<UserModelAgent>>,
                response: Response<ArrayList<UserModelAgent>>
            ) {
                if (response.isSuccessful) {

                    listAgent = response.body()

                    list_user!!.adapter = activity?.let {
                        listAgent?.let { it1
                            ->
                            ListAdapterAgent(it1, it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<UserModelAgent>>, t: Throwable) {
                Log.e("ERROR: ", t.message!!)
            }
        })
    }
}



