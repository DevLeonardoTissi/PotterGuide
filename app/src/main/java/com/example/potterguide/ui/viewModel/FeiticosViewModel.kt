package com.example.potterguide.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.potterguide.model.Feitico
import com.example.potterguide.repositorio.Repositorio

class FeiticosViewModel(
    private val repositorio: Repositorio,
    var erroAtualizacao: () -> Unit = {}
) : ViewModel() {

    var listaDeFeiticos = MutableLiveData<List<Feitico>>(emptyList())

    suspend fun buscaFeiticos(): LiveData<List<Feitico>> {
        try {
            listaDeFeiticos.postValue(repositorio.buscaFeiticos())
        } catch (_: Exception) {
            listaDeFeiticos.value?.let {
                if (it.isNotEmpty()) {
                    erroAtualizacao()
                }
            }
        }
        return listaDeFeiticos
    }

    fun search(query: String): List<Feitico>? {
        return listaDeFeiticos.value?.filter { it.nome.contains(query, true) }
    }


}