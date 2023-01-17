package com.example.potterguide.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.potterguide.model.Personagem
import com.example.potterguide.repositorio.PersonagemRepositorio

class PersonagensViewModel(
    private val personagemRepositorio: PersonagemRepositorio,
    var erroAtualizacao: () -> Unit = {}
) : ViewModel() {

    var listaDePersonagens = MutableLiveData<List<Personagem>>(emptyList())

    suspend fun buscaPersonagens(identificador: String): LiveData<List<Personagem>> {
        try {
            listaDePersonagens.postValue(personagemRepositorio.buscaPersonagens(identificador))
        } catch (e: Exception) {
            listaDePersonagens.value?.let {
                if (it.isNotEmpty()) {
                    erroAtualizacao()
                }
            }
        }

        return listaDePersonagens
    }

    fun search(query: String): List<Personagem>? {
        return listaDePersonagens.value?.filter { it.nome.contains(query, true) }
    }
}

