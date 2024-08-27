package com.br.orgs.ui.activity

import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.br.orgs.database.AppDatabase
import com.br.orgs.databinding.ActivityFormularioProdutoBinding
import com.br.orgs.extensions.tentaCarregarImagem
import com.br.orgs.ui.dialog.FormularioImagemDialog
import com.br.orgs.ui.recyclerview.adapter.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFormularioProdutoBinding.inflate(layoutInflater) }

    private var url: String? = null
    private var idProduto = 0L

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


        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            title = "Alterar Produto"
            url = produtoCarregado.imagem
            idProduto = produtoCarregado.id
            binding.activityFormularioProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem)
            binding.activityFormularioProdutoNome.setText(produtoCarregado.nome)
            binding.activityFormularioProdutoDescricao.setText(produtoCarregado.descricao)
            binding.activityFormularioProdutoValor.setText(produtoCarregado.valor.toPlainString())
        }


    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar

        botaoSalvar.setOnClickListener {

            //Criando e definindo o banco de dados.
            val db = AppDatabase.instance(this)
            val dao = db.produtoDao()

            val produtoNovo = criarProduto()

            if(idProduto > 0){
                dao.altera(produtoNovo)
            }else{
                dao.salva(produtoNovo)
            }

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
            id = idProduto,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}