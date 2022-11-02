package com.example.potterguide.repositorio

import android.content.Context
import com.example.potterguide.R
import com.example.potterguide.model.Personagem
import com.example.potterguide.webclient.RetrofitInicializador
import com.example.potterguide.webclient.model.PersonagemResposta
import com.example.potterguide.webclient.services.HarryPotterService

class Repositorio(private val context: Context) {

     suspend fun busca(texto: String): List<Personagem> {
        val listaResposta = identificaLista(texto)
        val listapersonagem = listaResposta.map { personagensResposta ->
            personagensResposta.personagem
        }
        return listapersonagem
    }

    suspend fun identificaLista(texto: String): List<PersonagemResposta> {

        val harrypotterservice: HarryPotterService = RetrofitInicializador().harryPotterService

        return when (texto) {
            context.getString(R.string.todosOsPersonagens) ->  harrypotterservice.buscaTodos()
            context.getString(R.string.alunos) ->  harrypotterservice.buscaTodosAlunos()
            context.getString(R.string.funcionarios) ->  harrypotterservice.buscaTodosFuncionarios()
            context.getString(R.string.alunosGrifinoria) ->  harrypotterservice.buscaTodosGrifinoria()
            context.getString(R.string.alunosSonserina) ->  harrypotterservice.buscaTodosSonserina()
            context.getString(R.string.alunosLufalufa) ->  harrypotterservice.buscaTodosLufaLufa()
            context.getString(R.string.alunosCorvinal) ->  harrypotterservice.buscaTodosCorvinal()

            else -> emptyList()

        }
    }
}