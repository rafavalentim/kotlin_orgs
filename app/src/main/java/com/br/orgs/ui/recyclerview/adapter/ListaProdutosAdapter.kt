package com.br.orgs.ui.recyclerview.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.br.orgs.dao.DetalheProdutoDao
import com.br.orgs.databinding.ProdutoItemBinding
import com.br.orgs.extensions.tentaCarregarImagem
import com.br.orgs.model.DetalheProduto
import com.br.orgs.ui.activity.DetalheProdutoActivity
import com.br.orgs.ui.recyclerview.adapter.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class ListaProdutosAdapter(

    private val context : Context,
    produtos : List<Produto>

) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()


    class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun vincula(produto: Produto) {

            val nome = binding.nome
            val descricao = binding.descricao
            val valor =   binding.valor


            nome.text = produto.nome
            descricao.text = produto.descricao
            val valorEmMoeda = formataParaMoedaBrasileira(produto.valor)
            valor.text = valorEmMoeda //Converte de BigDecimal para string.


            val visibilidade = if(produto.imagem != null){
                View.VISIBLE
            }else{
                View.GONE
            }

            binding.imageView.visibility = visibilidade

            binding.imageView.tentaCarregarImagem(produto.imagem)

            configuraCliqueCardView(nome.text.toString(), descricao.text.toString(), valor.text.toString(), produto.imagem)

        }

        private fun configuraCliqueCardView(nome: String, descricao: String, valor: String, imagem: String?) {
            val cardView = binding.produtoAdapterCardView

            //Pegando o context do binding para poder chamar a Intent dentro do Adapter.
            val activity = binding.produtoAdapterCardView.context as Activity

            cardView.setOnClickListener {

                //Adicionando as informações do Produto a serem passados para a próxima tela.

                var url : String = ""

                imagem?.let {
                    url = imagem
                }

                var detalhe =  DetalheProduto(
                    imagem = url,
                    preco = valor,
                    textoPrincipal = nome,
                    textoDetalhe = descricao
                )

                val intent = Intent(activity, DetalheProdutoActivity::class.java).apply {
                    putExtra("detalheProduto", detalhe) //O Apply faz a passagem de parâmetros.
                }
                activity.startActivity(intent)
            }
        }

        private fun formataParaMoedaBrasileira(valor: BigDecimal): String? {

            val formatadorFinanceiro = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            return formatadorFinanceiro.format(valor)
        }
    }

    //Criando a classe ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int
    ): ViewHolder {

        val binding = ProdutoItemBinding
            .inflate(
                LayoutInflater.from(context),
                parent,
                false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}