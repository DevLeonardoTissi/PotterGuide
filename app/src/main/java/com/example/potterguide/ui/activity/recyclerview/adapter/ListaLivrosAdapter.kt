package com.example.potterguide.ui.activity.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.potterguide.databinding.LivroItemBinding
import com.example.potterguide.extensions.addCaractere
import com.example.potterguide.extensions.tentaCarregarImagem
import com.example.potterguide.model.Livro

class ListaLivrosAdapter(
    var quandoClicaNoItem: (livro: Livro) -> Unit = {},
) : androidx.recyclerview.widget.ListAdapter<Livro, ListaLivrosAdapter.LivrosViewHolder>(
    differcallback
) {

    inner class LivrosViewHolder(
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
            binding.ImagemLivro.tentaCarregarImagem(
                livro.imageLinks?.thumbnail?.addCaractere(
                    's',
                    4
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivrosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LivroItemBinding.inflate(inflater, parent, false)
        return LivrosViewHolder(binding)

    }


    override fun onBindViewHolder(holder: LivrosViewHolder, position: Int) {
        holder.vincula(getItem(position))
    }

    companion object {
        private val differcallback = object : DiffUtil.ItemCallback<Livro>() {
            override fun areItemsTheSame(oldItem: Livro, newItem: Livro): Boolean {
                return oldItem.titulo == newItem.titulo
            }

            override fun areContentsTheSame(oldItem: Livro, newItem: Livro): Boolean {
                return oldItem.titulo == newItem.titulo
            }

        }
    }
}