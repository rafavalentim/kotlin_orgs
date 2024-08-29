package com.br.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.orgs.database.AppDatabase
import com.br.orgs.databinding.ActivityListaProdutosBinding
import com.br.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaProdutosActivity : AppCompatActivity() {


    private val adapter = ListaProdutosAdapter(context = this)
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

            //Criando e definindo o banco de dados.
            val db = AppDatabase.instance(this)
            val produtosDao = db.produtoDao()

        //Usando o CoroutineExceptionHandler
            val handler = CoroutineExceptionHandler{ coroutineContext, throwable ->
                Toast.makeText(this@ListaProdutosActivity,"Ocorreu um problema", Toast.LENGTH_SHORT).show()

            }

            //Criando uma coroutine.
            val scope = MainScope()
            scope.launch(handler) { //Usando o handler no lugar do try/catch.
                //O Bloco de exceção deve estar dentro do esopo da coroutine.
                    val produtos = withContext(Dispatchers.IO){
                        produtosDao.buscaTodos()
                    } //Dispatcher IO diz que o sistema será executado em uma thread nova.
                    adapter.atualiza(produtos)
            }
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

        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalheProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }



    }
}