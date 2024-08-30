package com.br.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.br.orgs.database.AppDatabase
import com.br.orgs.databinding.ActivityListaProdutosBinding
import com.br.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ListaProdutosActivity : AppCompatActivity() {


    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater) //Usando ViewBinding
    }

    private val dao by lazy{

        //Criando e definindo o banco de dados.
        val db = AppDatabase.instance(this)
        db.produtoDao()
    }

    //private val job = Job() //para poder cancelar a coroutine.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) //Necessário para ViewBinding funcionar

        configuraRecyclerView()
        configuraFloatActionButton()
//
//        val fluxoDeNumeros =  flow<Int> {
//
//            repeat(100){
//                emit(1)
//                delay(1000)
//
//            }
//        }
//
//        lifecycleScope.launch {
//            fluxoDeNumeros.collect{ numero ->
//
//                Log.i("ListaNumeros", "onCreate: $numero")
//
//            }
//        }

        lifecycleScope.launch(/*handler*/) { //Usando o handler no lugar do try/catch.
            //Usando o Flow.
            dao.buscaTodos().collect{produtos ->
                adapter.atualiza(produtos)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        //Usando o CoroutineExceptionHandler
//            val handler = CoroutineExceptionHandler{ coroutineContext, throwable ->
//                Toast.makeText(this@ListaProdutosActivity,"Ocorreu um problema", Toast.LENGTH_SHORT).show()
//            }


            //Simulando uma coroutine de execução contínua | OBS: nos parametros é possivel concatenar parametros.
            //val jobPrimario = scope.launch(job + handler + Dispatchers.IO + CoroutineName("primaria")) {
//            scope.launch(job) {
//
//            repeat(1000){
//                    Log.i("onResume", "onResume: coroutine em execução $it")
//                    delay(1000)
//                    job.cancel() //cancelando uma coroutine específica por meio dp job.
//                }
//            }


    }

//    private suspend fun buscaTodosOsProdutos(): List<Produto> {
//        val produtos = withContext(Dispatchers.IO) {
//            dao.buscaTodos()
//        } //Dispatcher IO diz que o sistema será executado em uma thread nova.
//        return produtos
//    }

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

//    override fun onDestroy() {
//        super.onDestroy()
//        job.cancel() //Cancelando o Job no onDestroy.
//    }

}