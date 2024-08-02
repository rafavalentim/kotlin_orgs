package com.br.orgs.extensions

import android.widget.ImageView
import coil.load
import com.br.orgs.R

fun ImageView.tentaCarregarImagem(url : String? = null){

    load(url) {
        fallback(R.drawable.ic_launcher_background)
        error(R.drawable.error_generic)
        placeholder(R.drawable.placeholder)
    }

}