package com.br.orgs.ui.activity

import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.br.orgs.R
import com.br.orgs.dao.ProdutosDao
import com.br.orgs.databinding.ActivityFormularioProdutoBinding
import com.br.orgs.databinding.FormularioAlertImagemBinding
import com.br.orgs.extensions.tentaCarregarImagem
import com.br.orgs.ui.dialog.FormularioImagemDialog
import com.br.orgs.ui.recyclerview.adapter.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val dao = ProdutosDao()

    private val binding by lazy { ActivityFormularioProdutoBinding.inflate(layoutInflater) }

    private var url: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)
        title = "Cadastrar Produto"
        configuraBotaoSalvar()
        binding.activityFormularioProdutoImagem.setOnClickListener {

            FormularioImagemDialog(this)
                .mostra(url){ imagem ->
                    url = imagem
                    binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
            }

        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar

        botaoSalvar.setOnClickListener {

            val produtoNovo = criarProduto()
            dao.adicionar(produtoNovo)
            finish() //volta para a activity anterior.
        }
    }

    private fun criarProduto(): Produto {

        val campoNome = binding.activityFormularioProdutoNome
        val campoDescricao = binding.activityFormularioProdutoDescricao
        val campoValor = binding.activityFormularioProdutoValor

        val nome = campoNome.text.toString()
        val descricao = campoDescricao.text.toString()

        //Validando o campo BigDecimal para aceitar vazio.
        val valorEmTexto = campoValor.text.toString()

        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}