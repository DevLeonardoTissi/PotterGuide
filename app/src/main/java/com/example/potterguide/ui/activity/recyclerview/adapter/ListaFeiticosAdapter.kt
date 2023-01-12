package com.example.potterguide.ui.activity.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.potterguide.databinding.FeiticoItemBinding
import com.example.potterguide.model.Feitico

class ListaFeiticosAdapter() :
    ListAdapter<Feitico, ListaFeiticosAdapter.FeiticosViewHolder>(differcallback) {

    class FeiticosViewHolder(
        private val binding: FeiticoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun vincula(feitico: Feitico) {
            binding.nomeFeitico.text = feitico.nome
            binding.DescricaoFeitico.text = feitico.descricao
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeiticosViewHolder =
        FeiticosViewHolder(
            FeiticoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: FeiticosViewHolder, position: Int) {
        holder.vincula(getItem(position))
    }


    companion object {
        private val differcallback = object : DiffUtil.ItemCallback<Feitico>() {
            override fun areItemsTheSame(oldItem: Feitico, newItem: Feitico): Boolean {
                return oldItem.nome == newItem.nome
            }

            override fun areContentsTheSame(oldItem: Feitico, newItem: Feitico): Boolean {
                return oldItem.nome == newItem.nome
            }
        }
    }
}
