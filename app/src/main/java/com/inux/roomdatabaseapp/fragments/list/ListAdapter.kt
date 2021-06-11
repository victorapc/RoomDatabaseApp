package com.inux.roomdatabaseapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.inux.roomdatabaseapp.R
import com.inux.roomdatabaseapp.interfacelistener.UpdateClickListener
import com.inux.roomdatabaseapp.model.User
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter(private val listener: UpdateClickListener) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.primeiroNome_txt.text = currentItem.firstName
        holder.itemView.sobrenome_txt.text = currentItem.lastName
        holder.itemView.idade_txt.text = currentItem.age.toString()
        holder.itemView.endereco_text.text = currentItem.endereco.endereco

        var numeroEndereco = ""
        if(currentItem.endereco.numero != 0){
            numeroEndereco = currentItem.endereco.numero.toString()
        }
        holder.itemView.numero_text.text = numeroEndereco

        holder.itemView.setOnClickListener {
            listener.usuarioClickedItem(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }
}