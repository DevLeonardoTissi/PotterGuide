package com.example.potterguide.ui.activity.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.potterguide.databinding.LivroItemBinding
import com.example.potterguide.extensions.addCaractere
import com.example.potterguide.extensions.tentaCarregarImagem
import com.example.potterguide.model.Livro

class ListaLivrosAdapter(
    private val context: Context,
    var quandoClicaNoItem: (livro: Livro) -> Unit = {},
    livros: List<Livro> = emptyList()
) : RecyclerView.Adapter<ListaLivrosAdapter.ViewHolder>() {

    private val livros = livros.toMutableList()

    inner class ViewHolder(
        private val binding: LivroItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var livro: Livro

        init {
            itemView.setOnClickListener {
                if (::livro.isInitialized) {
                    quandoClicaNoItem(livro)
                }
            }
        }

        fun vincula(livro: Livro) {
            this.livro = livro
            binding.nomeLivro.text = livro.titulo

            val visibilidade = if (livro.imageLinks?.thumbnail != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.ImagemLivro.visibility = visibilidade
            binding.ImagemLivro.tentaCarregarImagem(livro.imageLinks?.thumbnail?.addCaractere('s',4))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = LivroItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vincula(livros[position])
    }

    override fun getItemCount(): Int = livros.size

    fun atualiza(livros: List<Livro>) {
        notifyItemRangeRemoved(0, this.livros.size)
        this.livros.clear()
        this.livros.addAll(livros)
        notifyItemInserted(this.livros.size)
    }
}