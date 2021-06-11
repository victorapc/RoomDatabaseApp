package com.inux.roomdatabaseapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.inux.roomdatabaseapp.R
import com.inux.roomdatabaseapp.model.Endereco
import com.inux.roomdatabaseapp.model.User
import com.inux.roomdatabaseapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.fragment_add.*

class UpdateActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var item: User
    private lateinit var primeiroNomeEt: EditText
    private lateinit var sobrenomeEt: EditText
    private lateinit var idadeEt: EditText
    private lateinit var enderecoEt: EditText
    private lateinit var numeroEt: EditText

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        //Adicionar o voltar no toolbar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        this.title = "Atualizar Registro"

        //Pegando dados da intent enviada via Parceable.
        item = intent.extras?.getParcelable("usuario")!!

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        primeiroNomeEt = updatePrimeiroNome_Et
        primeiroNomeEt.setText(item.firstName)

        sobrenomeEt = updateSobrenome_Et
        sobrenomeEt.setText(item.lastName)

        idadeEt = updateIdade_Et
        idadeEt.setText(item.age.toString())

        enderecoEt = updateEndereco_Et
        enderecoEt.setText(item.endereco.endereco)

        numeroEt = updateNumero_Et
        numeroEt.setText(item.endereco.numero.toString())

        update_btn.setOnClickListener {
            updateItem()
        }
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
            val endereco = Endereco(endereco, numeroEndereco)
            // Criando objeto user
            val updatedUser = User(
                item.id,
                primeiroNome,
                sobrenome,
                Integer.parseInt(idade.toString()),
                endereco
            )
            // Atualizando usuário
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(this, "Registro atualizado com sucesso!", Toast.LENGTH_LONG).show()

            finish()
        }else{
            Toast.makeText(this, "Preencha os dados para atualizar!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Obriga a preencher todos os dados nos campos.
     */
    private fun inputCkeck(primeiroNome: String, segundoNome: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(primeiroNome) && TextUtils.isEmpty(segundoNome) && age.isEmpty())
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Sim"){ _,_ ->
            mUserViewModel.deleteUser(item)
            Toast.makeText(this,
                "Usuário ${item.firstName} excluído com sucesso!",
                Toast.LENGTH_LONG).show()

            finish()
        }
        builder.setNegativeButton("Não"){ _,_ ->}
        builder.setTitle("Delete ${item.firstName}?")
        builder.setMessage("Deseja deletar o usuário ${item.firstName}?")
        builder.create().show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete ->
                deleteUser()
            else -> false
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
}