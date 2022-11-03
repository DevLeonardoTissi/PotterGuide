package com.example.potterguide.ui.activity.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.potterguide.databinding.FeiticoItemBinding
import com.example.potterguide.model.Feitico

class ListaFeiticosAdapter(private val context: Context, feiticos: List<Feitico> = emptyList()) :
    RecyclerView.Adapter<ListaFeiticosAdapter.ViewHolder>() {

    private val feiticos = feiticos.toMutableList()

    class ViewHolder(
        private val binding: FeiticoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun vincula(feitico : Feitico) {
            binding.nomeFeitico.text = feitico.nome
            binding.DescricaoFeitico.text = feitico.descricao
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        FeiticoItemBinding.inflate(
            LayoutInflater.from(context), parent ,false
        )
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.vincula(feiticos[position])
}

override fun getItemCount(): Int = feiticos.size

    fun atualiza(feiticos:List<Feitico>){
        notifyItemRangeRemoved(0,this.feiticos.size)
        this.feiticos.clear()
        this.feiticos.addAll(feiticos)
        notifyItemInserted(this.feiticos.size)
    }


}
