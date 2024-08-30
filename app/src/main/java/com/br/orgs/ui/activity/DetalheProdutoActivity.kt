package com.br.orgs.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.br.orgs.R
import com.br.orgs.database.AppDatabase
import com.br.orgs.databinding.ActivityDetalheProdutoBinding
import com.br.orgs.extensions.tentaCarregarImagem
import com.br.orgs.model.DetalheProduto
import com.br.orgs.ui.recyclerview.adapter.model.Produto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class DetalheProdutoActivity : AppCompatActivity() {

    private var produtoId: Long = 0L
    private var produto: Produto? = null

    private val binding by lazy {
        ActivityDetalheProdutoBinding.inflate(layoutInflater)
    }

    val produtoDao by lazy{
        AppDatabase.instance(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root) //Usando viewBinding
        tentarCarregarProduto()

        //Pegando as informações da tela
        val imagem = binding.imageViewDetalheItem
        val preco = binding.formImagemPrecoItem
        val titulo = binding.textViewTitle
        val descricao = binding.textViewDescricao

        val detalheProduto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //método novo para os SDK mais novos
            intent.getParcelableExtra("detalheProduto")
        } else {
            //método deprecated  para os SDK mais antigos
            intent.getParcelableExtra<DetalheProduto>("detalheProduto")
        }

        detalheProduto?.let {

            val strUrl = detalheProduto.imagem.toString()
            imagem.tentaCarregarImagem(strUrl)

            preco.text = detalheProduto.preco.toString()
            titulo.text = detalheProduto.textoPrincipal.toString()
            descricao.text = detalheProduto.textoDetalhe.toString()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
                R.id.menu_detalhe_produto_remover -> {
                    //Usando Coroutines
                    lifecycleScope.launch {
                        produto?.let { produtoDao.remove(it) }
                        finish()
                    }

                }
                R.id.menu_detalhe_produto_editar -> {
                    Intent(this, FormularioProdutoActivity::class.java).apply {
                        putExtra(CHAVE_PRODUTO_ID, produtoId)
                        startActivity(this)
                    }
                }
            }

        return super.onOptionsItemSelected(item)
    }

    private fun tentarCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            imageViewDetalheItem.tentaCarregarImagem(produtoCarregado.imagem)
            textViewTitle.text = produtoCarregado.nome
            textViewDescricao.text = produtoCarregado.descricao
            formImagemPrecoItem.text = formataParaMoedaBrasileira(produtoCarregado.valor)
        }
    }

    private fun formataParaMoedaBrasileira(valor: BigDecimal): String? {
        val formatadorFinanceiro = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return formatadorFinanceiro.format(valor)
    }

    override fun onResume() {
        super.onResume()
        buscaProduto()
    }

    private fun buscaProduto() {
        //Usando o Coroutines.
        lifecycleScope.launch {

            produtoDao.buscaPorId(produtoId).collect{ prod ->
                let {
                    title = "Alterar Produto"
                    if (prod != null) {
                        preencheCampos(prod)
                    }
                }
            }
            finish()

//            produto = produtoDao.buscaPorId(produtoId)
//                produto?.let {
//                    preencheCampos(it)
//                } ?: finish()
        }
    }
}
