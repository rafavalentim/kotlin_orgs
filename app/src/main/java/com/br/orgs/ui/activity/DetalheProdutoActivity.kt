package com.br.orgs.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.br.orgs.R
import com.br.orgs.database.AppDatabase
import com.br.orgs.databinding.ActivityDetalheProdutoBinding
import com.br.orgs.extensions.tentaCarregarImagem
import com.br.orgs.model.DetalheProduto
import com.br.orgs.ui.recyclerview.adapter.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

private const val TAG = "DetalhesProduto"

class DetalheProdutoActivity : AppCompatActivity() {

    private lateinit var produto: Produto

    private val binding by lazy {
        ActivityDetalheProdutoBinding.inflate(layoutInflater)
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
        if (::produto.isInitialized) {
            val db = AppDatabase.instance(this)
            val produtoDao = db.produtoDao()
            when(item.itemId){
                R.id.menu_detalhe_produto_remover -> {
                    produtoDao.remove(produto)
                    finish()
                }
                R.id.menu_detalhe_produto_editar -> {
                    Intent(this, FormularioProdutoActivity::class.java).apply {
                        putExtra(CHAVE_PRODUTO, produto)
                        startActivity(this)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentarCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            produto = produtoCarregado
            preencheCampos(produtoCarregado)
        } ?: finish()
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

}
