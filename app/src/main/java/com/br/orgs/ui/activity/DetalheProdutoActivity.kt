package com.br.orgs.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.br.orgs.databinding.ActivityDetalheProdutoBinding
import com.br.orgs.extensions.tentaCarregarImagem
import com.br.orgs.model.DetalheProduto

class DetalheProdutoActivity : AppCompatActivity() {

    //private val dao = DetalheProdutoDao

    private val binding by lazy {
        ActivityDetalheProdutoBinding.inflate(layoutInflater)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root) //Usando viewBinding

        //Pegando as informações da tela
        val imagem = binding.imageViewDetalheItem
        val preco = binding.formImagemPrecoItem
        val titulo = binding.textViewTitle
        val descricao = binding.textViewDescricao

        val detalheProduto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //método novo para os SDK mais novos
            intent.getParcelableExtra("detalheProduto")
        } else{
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
}