package com.example.potterguide.repositorio

import android.content.Context
import com.example.potterguide.R
import com.example.potterguide.model.Personagem
import com.example.potterguide.webclient.model.PersonagemResposta
import com.example.potterguide.webclient.services.HarryPotterService

class PersonagemRepositorio(private val context: Context, private val harrypotterservice: HarryPotterService ) {


    suspend fun buscaPersonagens(identificador: String): List<Personagem> {
        val listaResposta = identificaLista(identificador).map { personagensResposta ->
            personagensResposta.personagem
        }
        return listaResposta
    }

    suspend fun identificaLista(identificador: String): List<PersonagemResposta> {
        return when (identificador) {
            context.getString(R.string.todosOsPersonagens) -> harrypotterservice.buscaTodos()
            context.getString(R.string.alunos) -> harrypotterservice.buscaTodosAlunos()
            context.getString(R.string.funcionarios) -> harrypotterservice.buscaTodosFuncionarios()
            context.getString(R.string.alunosGrifinoria) -> harrypotterservice.buscaTodosGrifinoria()
            context.getString(R.string.alunosSonserina) -> harrypotterservice.buscaTodosSonserina()
            context.getString(R.string.alunosLufalufa) -> harrypotterservice.buscaTodosLufaLufa()
            context.getString(R.string.alunosCorvinal) -> harrypotterservice.buscaTodosCorvinal()

            else -> emptyList()
        }
    }




}
