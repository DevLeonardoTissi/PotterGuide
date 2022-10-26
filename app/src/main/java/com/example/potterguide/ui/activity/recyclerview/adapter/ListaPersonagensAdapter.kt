package com.example.potterguide.ui.activity.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.potterguide.databinding.PersonagemItemBinding
import com.example.potterguide.extensions.tentaCarregarImagem
import com.example.potterguide.model.Personagem

class ListaPersonagensAdapter(
    private val context:Context,
    personagens:List<Personagem> = emptyList(),
    var quandoClicaNoItem: (personagem: Personagem) -> Unit = {}
) : RecyclerView.Adapter<ListaPersonagensAdapter.ViewHolder>() {

    private val personagens = personagens.toMutableList()

   inner class ViewHolder(
        private val binding: PersonagemItemBinding
    ): RecyclerView.ViewHolder(binding.root){

       private lateinit var personagem: Personagem

       init {
           itemView.setOnClickListener {
               if (::personagem.isInitialized){
                   quandoClicaNoItem(personagem)
               }
           }
       }

        fun vincula(personagem:Personagem){
            this.personagem = personagem
           binding.personagemItemImagem.tentaCarregarImagem(personagem.imagem)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.vincula(personagens[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(PersonagemItemBinding.inflate(
        LayoutInflater.from(context)))



    override fun getItemCount(): Int = personagens.size

    fun atualiza(personagens:List<Personagem>){
        notifyItemRangeRemoved(0,this.personagens.size)
       this.personagens.clear()
        this.personagens.addAll(personagens)
        notifyItemInserted(this.personagens.size)
    }


}
