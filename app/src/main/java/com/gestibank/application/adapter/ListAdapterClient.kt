package com.gestibank.application.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gestibank.application.R
import com.gestibank.application.api.APIService
import com.gestibank.application.api.ClientService
import com.gestibank.application.models.UserModel
import com.gestibank.application.models.UserModelAgent
import kotlinx.android.synthetic.main.item_list.view.txt_email
import kotlinx.android.synthetic.main.item_list.view.txt_index
import kotlinx.android.synthetic.main.item_list.view.txt_name
import kotlinx.android.synthetic.main.item_list.view.txt_tel
import kotlinx.android.synthetic.main.item_list_client.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListAdapterClient(
    val items: ArrayList<UserModel>,
//    var user: UserModel,
    val context: Context,
    val listAgent: ArrayList<UserModelAgent>?
) : RecyclerView.Adapter<ViewHolderClient>() {
    var data = ArrayList<String>()
    var email : String = ""
    var agentSelected : String = ""

    var clientService: ClientService? = null
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClient {
        return ViewHolderClient(
            LayoutInflater.from(context).inflate(R.layout.item_list_client, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderClient, position: Int) {
        holder?.txt_index?.text = context.getString(R.string.client) + position
        holder?.txt_name?.text = "Name: " + items[position].prenom + " " + items[position].name
        holder?.txt_email?.text = "Email: " + items[position].email
        holder?.txt_tel?.text = "Phone: " + items[position].tel

       var  user=items[position]
        email=items[position].email


        clientService = APIService.getClientService()


        if (listAgent != null) {
            for (agent in listAgent) {
                data.add(agent.name + " " + agent.prenom)
            }
            holder?.spinner_list_agent?.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data)
            agentSelected=holder?.spinner_list_agent?.selectedItem.toString()
        }


        holder?.btn_assign_to.setOnClickListener {
            clientService?.let { it1 -> updateClientsEnAttentes(it1,email, user,agentSelected) }
        }

    }
}

class ViewHolderClient(view: View) : RecyclerView.ViewHolder(view) {
    val txt_name = view.txt_name
    val txt_email = view.txt_email
    val txt_tel = view.txt_tel
    val txt_index = view.txt_index
    val btn_assign_to = view.btn_assign_to
    var  spinner_list_agent = view.spinner_list_agent
}

fun updateClientsEnAttentes(
    clientService: ClientService,
    email: String,
    user: UserModel,
    agentSelected: String
) {

   user.agent=agentSelected

    val call: Call<UserModel> = clientService!!.updateUser(email,user)
    call.enqueue(object : Callback<UserModel> {
        override fun onResponse(
            call: Call<UserModel>,
            response: Response<UserModel>
        ) {
            if (response.isSuccessful) {



            }
        }

        override fun onFailure(call: Call<UserModel>, t: Throwable) {
            Log.e("ERROR: ", t.message!!)
        }
    })
}
