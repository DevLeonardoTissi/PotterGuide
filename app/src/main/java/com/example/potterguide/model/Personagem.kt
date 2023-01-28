package com.example.potterguide.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Personagem(
   val nome:String,
   val especie:String,
   val sexo: String,
   val casa: String,
   val dataDeNascimento : String,
   val anoNascimento: Int,
   val mago: Boolean,
   val ancestralidade: String,
   val corDosOlhos: String,
   val corDoCabelo: String,
   val patrono: String,
   val estudante:Boolean,
   val funcionario:Boolean,
   val ator: String,
   val vivo: Boolean,
   val imagem: String

): Parcelable