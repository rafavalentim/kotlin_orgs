package com.br.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.br.orgs.dao.ProdutosDao
import com.br.orgs.database.AppDatabase
import com.br.orgs.database.dao.ProdutoDao
import com.br.orgs.databinding.ActivityListaProdutosBinding
import com.br.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.br.orgs.ui.recyclerview.adapter.model.Produto
import java.math.BigDecimal

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

        //Criando e definindo o banco de dados.
        val db = AppDatabase.instance(this)
        val produtosDao = db.produtoDao()

        adapter.atualiza(produtosDao.buscaTodos())
    }

    override fun onResume() {
        super.onResume()

        //Criando e definindo o banco de dados.
        val db = AppDatabase.instance(this)
        val produtosDao = db.produtoDao()

        adapter.atualiza(produtosDao.buscaTodos())
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