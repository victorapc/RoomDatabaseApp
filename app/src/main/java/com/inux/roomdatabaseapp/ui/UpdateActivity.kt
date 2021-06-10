package com.inux.roomdatabaseapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.inux.roomdatabaseapp.R
import com.inux.roomdatabaseapp.model.User
import com.inux.roomdatabaseapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {
    private lateinit var item: User
    private lateinit var primeiroNome: TextView
    private lateinit var sobrenome: TextView
    private lateinit var idade: TextView

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

        primeiroNome = updatePrimeiroNome_Et
        primeiroNome.text = item.firstName

        sobrenome = updateSobrenome_Et
        sobrenome.text = item.lastName

        idade = updateIdade_Et
        idade.text = item.age.toString()

        update_btn.setOnClickListener {
            updateItem()
        }
    }

    private fun updateItem(){

        if(inputCkeck(primeiroNome.text.toString(), sobrenome.text.toString(), updateIdade_Et.text)){
            // Criando objeto user
            val updatedUser = User(
                item.id,
                primeiroNome.text.toString(),
                sobrenome.text.toString(),
                Integer.parseInt(idade.text.toString())
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}