package com.gestibank.application.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gestibank.application.R
import com.gestibank.application.models.UserModel
import com.gestibank.application.models.UserModelAgent
import kotlinx.android.synthetic.main.fragment_currency__conversion.*
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapterAgent(val items : ArrayList<UserModelAgent>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txt_index?.text = context.getString(R.string.agent)+position
        holder?.txt_name?.text = "Name: "+items[position].prenom+" "+items[position].name
        holder?.txt_email?.text = "Email: "+items[position].email
        holder?.txt_tel?.text = "Phone: "+items[position].tel
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val txt_name = view.txt_name
        val txt_email = view.txt_email
        val txt_tel = view.txt_tel
        val txt_index = view.txt_index
}