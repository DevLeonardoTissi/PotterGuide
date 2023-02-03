package com.example.potterguide.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.potterguide.model.Feitico
import com.example.potterguide.repositorio.FeiticoRepositorio

class FeiticosViewModel(
    private val repositorio: FeiticoRepositorio,
    var erroAtualizacao: () -> Unit = {}
) : ViewModel() {

    private var _listaDeFeiticos = MutableLiveData<List<Feitico>>(emptyList())
    var listaDeFeiticos: LiveData<List<Feitico>> = _listaDeFeiticos

    suspend fun buscaFeiticos() {
        try {
            _listaDeFeiticos.value = repositorio.buscaFeiticos()
        } catch (e: Exception) {
            _listaDeFeiticos.value?.let {
                if (it.isNotEmpty()) {
                    erroAtualizacao()
                }
            }
        }
    }

    fun search(query: String): List<Feitico>? {
        return listaDeFeiticos.value?.filter { it.nome.contains(query, true) }
    }


}