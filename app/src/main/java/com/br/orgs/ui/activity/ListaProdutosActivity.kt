package com.br.orgs.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.br.orgs.R
import com.br.orgs.dao.ProdutosDao
import com.br.orgs.databinding.ActivityListaProdutosBinding
import com.br.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity() {

    private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscaTodos())
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater) //Usando ViewBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) //Necessário para ViewBinding funcionar

        configuraRecyclerView()
        configuraFloatActionButton()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
    }

    private fun configuraFloatActionButton() {
        val fab = binding.listaProdutosFloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this, FormularioProdutoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configuraRecyclerView() {

        val recyclerView = binding.listaProdutosRecyclerView

        //Adicionando os dados do formulário no RecyclerView
        recyclerView.adapter = adapter
    }
}