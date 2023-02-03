package com.example.potterguide.ui.activity.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.potterguide.R
import com.example.potterguide.databinding.PersonagemItemBinding
import com.example.potterguide.extensions.tentaCarregarImagem
import com.example.potterguide.model.Personagem
import com.example.potterguide.ui.activity.PERSONAGENS_GRIFINORIA
import com.example.potterguide.ui.activity.PERSONAGENS_LUFA_LUFA
import com.example.potterguide.ui.activity.PERSONAGENS_SONSERINA

class ListaPersonagensAdapter(
    var quandoClicaNoItem: (personagem: Personagem) -> Unit = {}
) : androidx.recyclerview.widget.ListAdapter<Personagem, ListaPersonagensAdapter.PersonagensViewHolder>(
    differcallback
) {

    inner class PersonagensViewHolder(
        private val binding: PersonagemItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var personagem: Personagem

        init {
            itemView.setOnClickListener {
                if (::personagem.isInitialized) {
                    quandoClicaNoItem(personagem)
                }
            }
        }

        fun vincula(personagem: Personagem) {
            this.personagem = personagem
            binding.apply {
                buscaImagemPersonagem(personagem)
                personagemItemNome.text = personagem.nome
                buscaLogoPersonagemCasa(personagem)
            }
        }

        private fun PersonagemItemBinding.buscaImagemPersonagem(
            personagem: Personagem
        ) {
            if (personagem.imagem.isNotEmpty()) {
                personagemItemImagem.tentaCarregarImagem(personagem.imagem)
            } else {
                personagemItemImagem.tentaCarregarImagem()
            }
        }

        private fun PersonagemItemBinding.buscaLogoPersonagemCasa(
            personagem: Personagem
        ) {
            if (personagem.casa.isNotEmpty()) {
                personagemItemImagemViewCasa.visibility = View.VISIBLE
                personagemItemImagemViewCasa.load(verificaCasa(personagem.casa))
            } else {
                personagemItemImagemViewCasa.visibility = View.GONE
            }
        }

        private fun verificaCasa(personagemCasa: String): Int {
            return when (personagemCasa) {
                PERSONAGENS_GRIFINORIA -> R.drawable.gryffindor
                PERSONAGENS_SONSERINA -> R.drawable.slytherin
                PERSONAGENS_LUFA_LUFA -> R.drawable.hufflepuff
                else -> {R.drawable.ravenclaw}
            }
        }

    }

    override fun onBindViewHolder(
        holder: ListaPersonagensAdapter.PersonagensViewHolder, position: Int
    ) {
        holder.vincula(getItem(position))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonagensViewHolder =
        PersonagensViewHolder(
            PersonagemItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    companion object {
        private val differcallback = object : DiffUtil.ItemCallback<Personagem>() {
            override fun areItemsTheSame(oldItem: Personagem, newItem: Personagem): Boolean {
                return oldItem.nome == newItem.nome
            }

            override fun areContentsTheSame(oldItem: Personagem, newItem: Personagem): Boolean {
                return oldItem.nome == newItem.nome
            }

        }
    }


}
