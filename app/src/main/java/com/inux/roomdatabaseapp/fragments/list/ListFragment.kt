package com.inux.roomdatabaseapp.fragments.list

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inux.roomdatabaseapp.R
import com.inux.roomdatabaseapp.interfacelistener.UpdateClickListener
import com.inux.roomdatabaseapp.model.User
import com.inux.roomdatabaseapp.ui.MainActivity
import com.inux.roomdatabaseapp.ui.UpdateActivity
import com.inux.roomdatabaseapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var adapter : ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // ViewModel
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            // RecylerView
            adapter = ListAdapter(object : UpdateClickListener{
                override fun usuarioClickedItem(item: User) {
                    startActivity(Intent(requireContext(), UpdateActivity::class.java).apply {
                        putExtra("usuario", item)
                    })
                }
            })
            val recyclerView = view.recyclerview
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter

            adapter.setData(user)
        })

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun deleteTodosUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim"){ _,_ ->
            mUserViewModel.deleteAllUsers()
            Toast.makeText(requireContext(),
                "Todos usuários excluídos com sucesso!",
                Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("Não"){ _,_ ->}
        builder.setTitle("Delete todos usuários?")
        builder.setMessage("Deseja deletar todos os usuários?")
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val searchView = SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu?.findItem(R.id.menu_search).apply {
            actionView = searchView
        }
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_delete ->
                deleteTodosUsers()
            else-> false
        }

        return super.onOptionsItemSelected(item)
    }

    private fun searchDataBase(query: String){
        val searchQuery = "%$query%"

        mUserViewModel.searchDataBase(searchQuery).observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchDataBase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDataBase(query)
        }
        return true
    }
}