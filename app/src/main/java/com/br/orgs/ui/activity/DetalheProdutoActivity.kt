package com.br.orgs.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.br.orgs.R
import com.br.orgs.databinding.ActivityDetalheProdutoBinding
import com.br.orgs.extensions.tentaCarregarImagem
import com.br.orgs.model.DetalheProduto

private const val TAG = "DetalhesProduto"

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.menu_detalhe_produto_editar -> {
                Log.i(TAG, "OnOptionsItemSelected: editar" )
            }

            R.id.menu_detalhe_produto_remover ->{
                Log.i(TAG, "OnOptionsItemSelected: remover")

            }

        }

        return super.onOptionsItemSelected(item)
    }

}