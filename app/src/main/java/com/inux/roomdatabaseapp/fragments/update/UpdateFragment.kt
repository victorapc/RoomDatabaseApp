package com.inux.roomdatabaseapp.fragments.update

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inux.roomdatabaseapp.R
import com.inux.roomdatabaseapp.model.Endereco
import com.inux.roomdatabaseapp.model.User
import com.inux.roomdatabaseapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.updatePrimeiroNome_Et.setText(args.currentUser.firstName)
        view.updateSobrenome_Et.setText(args.currentUser.lastName)
        view.updateIdade_Et.setText(args.currentUser.age.toString())
        view.updateEndereco_Et.setText(args.currentUser.endereco.endereco)
        view.updateNumero_Et.setText(args.currentUser.endereco.numero.toString())

        view.update_btn.setOnClickListener {
            updateItem()
        }

        // Add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem(){
        val primeiroNome = updatePrimeiroNome_Et.text.toString()
        val sobrenome = updateSobrenome_Et.text.toString()
        val idade = updateIdade_Et.text
        val endereco = updateEndereco_Et.text.toString()
        val numero = updateNumero_Et.text

        if(inputCkeck(primeiroNome, sobrenome, idade)){
            var numeroEndereco = 0
            if(!numero.isEmpty()){
                numeroEndereco = Integer.parseInt(numero.toString())
            }
            val endereco = Endereco(endereco, numeroEndereco, "", "")
            // Criando objeto user
            val updatedUser = User(
                args.currentUser.id,
                primeiroNome,
                sobrenome,
                Integer.parseInt(idade.toString()),
                endereco
            )
            // Atualizando usuário
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Registro atualizado com sucesso!", Toast.LENGTH_LONG).show()

            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Preencha os dados para atualizar!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Obriga a preencher todos os dados nos campos.
     */
    private fun inputCkeck(primeiroNome: String, segundoNome: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(primeiroNome) && TextUtils.isEmpty(segundoNome) && age.isEmpty())
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim"){ _,_ ->
            mUserViewModel.deleteUser(args.currentUser)

            Toast.makeText(requireContext(),
                "Usuário ${args.currentUser.firstName} excluído com sucesso!",
                Toast.LENGTH_LONG).show()

            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Não"){ _,_ ->}
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Deseja deletar o usuário ${args.currentUser.firstName}?")
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete ->
                deleteUser()
            else -> false
        }
        return super.onOptionsItemSelected(item)
    }
}