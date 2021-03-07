package com.gestibank.application.fragments.users.admin.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gestibank.application.R
import com.gestibank.application.api.APIService
import com.gestibank.application.api.ClientService
import com.gestibank.application.models.UserModelAgent
import com.gestibank.myapplication.ui.admin.add.AddAgentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddAgentFragment : Fragment() {

    private lateinit var homeViewModel: AddAgentViewModel
    var btn_rquest: Button? = null


    var edit_name: EditText? = null
    var edit_email: EditText? = null
    var edit_phone: EditText? = null
    var edit_pwd: EditText? = null
    var edit_prenom: EditText? = null
    var img_logo: ImageView? = null
    var txt_information: TextView? = null


    var list_user: RecyclerView? = null
    var clientService: ClientService? = null

    lateinit var recycler_list: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_sign_up, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        clientService = APIService.getClientService()


        edit_name = activity?.findViewById(R.id.edit_name);
        edit_prenom = activity?.findViewById(R.id.edit_prenom);
        edit_phone = activity?.findViewById(R.id.edit_phone);
        edit_email = activity?.findViewById(R.id.edit_email);
        edit_pwd = activity?.findViewById(R.id.edit_pwd);
        img_logo = activity?.findViewById(R.id.img_logo_sign_up);
        txt_information = activity?.findViewById(R.id.txt_information);

        img_logo?.visibility = View.GONE
        txt_information?.text = "Details of Agent"


        btn_rquest = activity?.findViewById(R.id.btn_sign_up);
        btn_rquest?.setOnClickListener {
            val user = UserModelAgent(
                edit_name!!.text.toString(),
                edit_email!!.text.toString(),
                edit_phone!!.text.toString(),
                UUID.randomUUID().toString(),
                "AGENT",
                edit_prenom!!.text.toString(),
                edit_pwd!!.text.toString()
            )
            addNewAgent(user)
        }

    }


    private fun addNewAgent(user: UserModelAgent) {

        val call: Call<UserModelAgent> = clientService!!.addAgent(user)
        call.enqueue(object : Callback<UserModelAgent> {
            override fun onResponse(
                call: Call<UserModelAgent>,
                response: Response<UserModelAgent>
            ) {
                Log.e("onResponse: ", response.body().toString())
                if (response.isSuccessful) {
                    //     replaceFragment(AdminFragment())
                    Toast.makeText(activity, "isSuccessful", Toast.LENGTH_SHORT).show()
                    activity!!.onBackPressed();
                }
            }

            override fun onFailure(call: Call<UserModelAgent>, t: Throwable) {
                Log.e("ERROR: ", t.message!!)
            }
        })
    }
}



