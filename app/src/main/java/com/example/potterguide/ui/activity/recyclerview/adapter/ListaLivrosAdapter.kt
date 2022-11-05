package com.example.potterguide.ui.activity.recyclerview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.potterguide.databinding.LivroItemBinding
import com.example.potterguide.extensions.tentaCarregarImagem
import com.example.potterguide.model.Livro

class ListaLivrosAdapter(private val context: Context, livros: List<Livro> = emptyList()) :
    RecyclerView.Adapter<ListaLivrosAdapter.ViewHolder>() {

    private val livros = livros.toMutableList()

    class ViewHolder(
        private val binding: LivroItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun vincula(livro : Livro) {
            binding.nomeLivro.text = livro.titulo
            binding.DescricaoLivro.text = livro.subtitulo

            livro.imageLinks?.let {
                it.smallThumbnail?.let { link ->
                    binding.ImagemLivro.tentaCarregarImagem("https://books.google.com/books/content?id=qDYQCwAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api")
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LivroItemBinding.inflate(
            LayoutInflater.from(context), parent ,false
        )
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vincula(livros[position])
    }

    override fun getItemCount(): Int = livros.size

    fun atualiza(livros:List<Livro>){
        notifyItemRangeRemoved(0,this.livros.size)
        this.livros.clear()
        this.livros.addAll(livros)
        notifyItemInserted(this.livros.size)
    }
}