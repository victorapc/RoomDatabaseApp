package com.inux.roomdatabaseapp.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.inux.roomdatabaseapp.R
import com.inux.roomdatabaseapp.data.user.User
import com.inux.roomdatabaseapp.data.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.add_btn.setOnClickListener{
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val primeiroNome = addPrimeiroNome_Et.text.toString()
        val sobrenome = addSobrenome_Et.text.toString()
        val idade = addIdade_Et.text

        if(inputCkeck(primeiroNome, sobrenome, idade)){
            // Criar objeto do usuário.
            val user = User(0, primeiroNome, sobrenome, Integer.parseInt(idade.toString()))
            // Adicionar o registro no banco.
            mUserViewModel.addUser(user)

            Toast.makeText(context, "Registro adicionado com sucesso!", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(context, "Por favor insira todos os dados.", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Obriga a preencher todos os dados nos campos.
     */
    private fun inputCkeck(primeiroNome: String, segundoNome: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(primeiroNome) && TextUtils.isEmpty(segundoNome) && age.isEmpty())
    }
}