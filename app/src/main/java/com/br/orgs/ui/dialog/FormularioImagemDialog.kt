package com.br.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.br.orgs.databinding.FormularioAlertImagemBinding
import com.br.orgs.extensions.tentaCarregarImagem

class FormularioImagemDialog(private val context: Context) {

    fun mostra(urlPadrao: String? = null, quandoImagemCarregada: (imagem: String) -> Unit) {

        //Processo de inflate da view do Alert para ter acesso aos componentes internos.
        FormularioAlertImagemBinding
            .inflate(LayoutInflater.from(context)).apply {

                urlPadrao?.let {
                    formImagemImagem.tentaCarregarImagem(it)
                    formImagemInputText.setText(it)
                }

                formImagemBotaoCarregar.setOnClickListener {
                    val url = formImagemInputText.text.toString()
                    formImagemImagem.tentaCarregarImagem(url)
                }

                AlertDialog.Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        val url = formImagemInputText.text.toString()
                        quandoImagemCarregada(url)
                    }
                    .setNegativeButton("Cancelar") { _, _ -> }
                    .show()
            }
    }

}