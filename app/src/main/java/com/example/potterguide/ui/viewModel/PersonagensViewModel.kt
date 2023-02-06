package com.example.potterguide.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.potterguide.model.Personagem
import com.example.potterguide.repositorio.PersonagemRepositorio
import com.example.potterguide.ui.activity.TODOS_OS_PERSONAGENS

class PersonagensViewModel(
    private val personagemRepositorio: PersonagemRepositorio,
    var erroAtualizacao: () -> Unit = {},
    var erroConexao: () -> Unit = {}
) : ViewModel() {

    private var _listaDePersonagens = MutableLiveData<List<Personagem>>(emptyList())
    var listaDePersonagens: LiveData<List<Personagem>> = _listaDePersonagens

    private val _identificador = MutableLiveData<String>(TODOS_OS_PERSONAGENS)
    var identificador: LiveData<String> = _identificador

    fun setIdentificador(filtro: String) {
        _identificador.value = filtro
    }

    fun getIdentificador(): String {
        return identificador.value ?: TODOS_OS_PERSONAGENS
    }


    suspend fun buscaPersonagens() {
        try {
            _listaDePersonagens.value =
                personagemRepositorio.buscaPersonagens(getIdentificador())
        } catch (e: Exception) {
            _listaDePersonagens.value?.let {
                if (it.isNotEmpty()) {
                    erroAtualizacao()
                } else{
                    erroConexao()
                }
            }
        }
    }

    fun search(query: String): List<Personagem>? {
        return listaDePersonagens.value?.filter { it.nome.contains(query, true) }
    }
}

