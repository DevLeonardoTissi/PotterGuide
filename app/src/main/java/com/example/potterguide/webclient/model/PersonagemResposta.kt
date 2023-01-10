package com.example.potterguide.webclient.model

import com.example.potterguide.model.Personagem

class PersonagemResposta(
    val name: String?,
    val species: String?,
    val gender: String?,
    val house: String?,
    val dateOfBirth: String?,
    val yearOfBirth: Int?,
    val wizard: Boolean?,
    val ancestry: String?,
    val eyeColour: String?,
    val hairColour: String?,
    val patronus: String?,
    val hogwartsStudent: Boolean?,
    val hogwartsStaff: Boolean?,
    val actor: String?,
    val alive: Boolean?,
    val image: String?

) {

    val personagem: Personagem
        get() = Personagem(
            nome = name ?: "",
            especie = species ?: "",
            sexo = gender ?: "",
            casa = house ?: "",
            dataDeNascimento = dateOfBirth ?: "",
            anoNascimento = yearOfBirth ?: 0,
            mago = wizard ?: false,
            ancestralidade = ancestry ?: "",
            corDosOlhos = eyeColour ?: "",
            corDoCabelo = hairColour ?: "",
            patrono = patronus ?: "",
            estudante = hogwartsStudent ?: false,
            funcionario = hogwartsStaff ?: false,
            ator = actor ?: "",
            vivo = alive ?: false,
            imagem = image ?: ""

        )
}

