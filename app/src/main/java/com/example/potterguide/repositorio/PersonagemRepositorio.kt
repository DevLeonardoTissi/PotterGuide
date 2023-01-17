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
            CHAVE_TODOS_OS_PERSONAGENS -> harrypotterservice.buscaTodos()
            CHAVE_PERSONAGENS_ALUNOS -> harrypotterservice.buscaTodosAlunos()
            CHAVE_PERSONAGENS_fUNCIONARIOS -> harrypotterservice.buscaTodosFuncionarios()
            CHAVE_PERSONAGENS_GRIFINORIA -> harrypotterservice.buscaTodosGrifinoria()
            CHAVE_PERSONAGENS_SONSERINA -> harrypotterservice.buscaTodosSonserina()
            CHAVE_PERSONAGENS_LUFA_LUFA -> harrypotterservice.buscaTodosLufaLufa()
            CHAVE_PERSONAGENS_CORVINAL -> harrypotterservice.buscaTodosCorvinal()

            else -> emptyList()
        }
    }


}
