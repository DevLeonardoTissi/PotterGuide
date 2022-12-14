package com.example.potterguide.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.potterguide.model.Livro
import com.example.potterguide.repositorio.Repositorio

class LivrosViewModel(
    private val repositorio: Repositorio,
    var erroAtualizacao: () -> Unit = {}
) : ViewModel() {

    var listaDeLivros = MutableLiveData<List<Livro>>(emptyList())

    suspend fun buscaLivros(): LiveData<List<Livro>> {
        try {
            listaDeLivros.postValue(repositorio.buscaLivros())
        } catch (_: Exception) {
            listaDeLivros.value?.let {
                if (it.isNotEmpty()) {
                    erroAtualizacao()
                }
            }
        }
        return listaDeLivros
    }

    fun search(query: String): List<Livro>? {
        return listaDeLivros.value?.filter { it.titulo.contains(query, true) }
    }

}