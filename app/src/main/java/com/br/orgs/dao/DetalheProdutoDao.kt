package com.br.orgs.dao

import com.br.orgs.model.DetalheProduto

class DetalheProdutoDao( private val detalheProduto: DetalheProduto) {

    companion object {
        private var detalhe =  DetalheProduto(
                imagem = "https://global.discourse-cdn.com/business5/uploads/kotlinlang/original/2X/7/72dd2b977bf99a06b07c0a433238a3bd69d7b0ad.png",
                preco = "R$ 10.00",
                textoPrincipal = "Teste do Título Principal.",
            textoDetalhe = "Teste de inserção de objetos na tela de Detalhe de Produto. " +
                    "Vamos ver ser vai funcionar mesmo essa bagaça. Espero que funcione para eu " +
                    "pular para o próximo curso e consiga finalizar o desafio."
            )
    }

    fun adicionar(detalheProduto : DetalheProduto ) {

        detalhe = detalheProduto
    }
}