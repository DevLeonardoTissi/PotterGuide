package com.example.potterguide.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.potterguide.model.Livro
import com.example.potterguide.repositorio.LivroRepositorio

class LivrosViewModel(
    private val repositorio: LivroRepositorio,
    var erroAtualizacao: () -> Unit = {}
) : ViewModel() {

    private val _listaDeLivros = MutableLiveData<List<Livro>>(emptyList())
    var listaDeLivros : LiveData<List<Livro>> = _listaDeLivros

    suspend fun buscaLivros(): LiveData<List<Livro>> {
        try {
            _listaDeLivros.postValue(repositorio.buscaLivros())
        } catch (e: Exception) {
            _listaDeLivros.value?.let {
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