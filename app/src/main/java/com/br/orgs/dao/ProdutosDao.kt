package com.br.orgs.dao

import com.br.orgs.ui.recyclerview.adapter.model.Produto
import java.math.BigDecimal

class ProdutosDao {

    fun adicionar(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodos(): List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>(

            //Adicionando um produto para ver nos testes
            Produto(
                nome = "Banana",
                descricao = "Preço do Kg",
                valor = BigDecimal("5.00")
            )
        )
    }
}