package com.br.orgs.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DetalheProduto (
    val imagem: String,
    val preco: String,
    val textoPrincipal: String,
    val textoDetalhe: String
) : Parcelable { }