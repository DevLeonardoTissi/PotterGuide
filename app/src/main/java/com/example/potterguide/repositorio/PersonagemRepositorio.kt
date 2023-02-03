package com.example.potterguide.repositorio

import com.example.potterguide.model.Personagem
import com.example.potterguide.ui.activity.*
import com.example.potterguide.webclient.model.PersonagemResposta
import com.example.potterguide.webclient.services.HarryPotterService

class PersonagemRepositorio(
    private val harrypotterservice: HarryPotterService
) {
    suspend fun buscaPersonagens(identificador: String): List<Personagem> {
        val listaResposta = identificaLista(identificador).map { personagensResposta ->
            personagensResposta.personagem
        }
        return listaResposta
    }

    suspend fun identificaLista(identificador: String): List<PersonagemResposta> {
        return when (identificador) {
            TODOS_OS_PERSONAGENS -> harrypotterservice.buscaTodos()
            PERSONAGENS_GRIFINORIA -> harrypotterservice.buscaTodosGrifinoria()
            PERSONAGENS_SONSERINA -> harrypotterservice.buscaTodosSonserina()
            PERSONAGENS_LUFA_LUFA -> harrypotterservice.buscaTodosLufaLufa()
            PERSONAGENS_CORVINAL -> harrypotterservice.buscaTodosCorvinal()

            else -> emptyList()
        }
    }
}
