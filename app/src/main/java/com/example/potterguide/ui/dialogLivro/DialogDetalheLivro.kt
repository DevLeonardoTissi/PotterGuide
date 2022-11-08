package com.example.potterguide.ui.dialogLivro

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.potterguide.databinding.DialogDetalheLivroBinding
import com.example.potterguide.extensions.addCaractere
import com.example.potterguide.extensions.tentaCarregarImagem
import com.example.potterguide.model.Livro

class DialogDetalheLivro ( private val context: Context, private val livro: Livro) {


    fun mostra(){
        DialogDetalheLivroBinding.inflate(LayoutInflater.from(context)).apply {
            preencheCampos()
            AlertDialog.Builder(context)
                .setView(root)
                .show()
        }
    }

    private fun DialogDetalheLivroBinding.preencheCampos() {
        dialogDetalheLivroNome.text = livro.titulo
        verificaSeVazio(dialogDetalheLivroSubtitulo, livro.subtitulo)
        verificaSeVazio(dialogDetalheLivroEditora, livro.editora, dialogDetalheLivroEditoraTitulo)
        verificaSeVazio(
            dialogDetalheLivroDataPublicacao,
            livro.dataDePublicacao,
            dialogDetalheLivroDataPublicacaoTitulo
        )
        verificaSeVazio(
            dialogDetalheLivroNumeroDePaginas,
            livro.numeroDePaginas.toString(),
            dialogDetalheLivroNumeroDePaginasTitulo
        )
        verificaSeVazio(
            dialogDetalheLivroCategorias,
            removeprimeiroeultimocaractere(livro.categorias.toString()),
            dialogDetalheLivroCategoriasTitulo
        )
        verificaSeVazio(dialogDetalheLivroIdiomas, livro.idioma, dialogDetalheLivroIdiomasTitulo)
        dialogDetalheLivroDescricao.text = livro.descricao
        dialogDetalheLivroAutores.text = removeprimeiroeultimocaractere(livro.autores.toString())


        val visibilidade = if (livro.imageLinks?.thumbnail != null) {
            View.VISIBLE
        } else {
            View.GONE
        }
        dialogDetalheLivroImagem.visibility = visibilidade
        dialogDetalheLivroImagem.tentaCarregarImagem(
            livro.imageLinks?.thumbnail?.addCaractere(
                's',
                4
            )
        )
    }

    private fun verificaSeVazio(textview: TextView, caracteristicaLivro : String? = null, textviewTitulo: TextView? = null) {
        if (caracteristicaLivro?.isEmpty() == true){
            textview.visibility = View.GONE
            textviewTitulo?.visibility = View.GONE
        }else{
            textview.text = caracteristicaLivro
        }
    }

    private fun removeprimeiroeultimocaractere(string : String): String{
        return string.substring(1, string.length -1);
    }

}