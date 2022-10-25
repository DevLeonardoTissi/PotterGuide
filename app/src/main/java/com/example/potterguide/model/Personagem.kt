package com.example.potterguide.model

class Personagem(
   val nome:String,
   val nomeAlternativo: List<String>? = null,
   val especie:String? = null,
   val sexo: String? = null,
   val casa: String? = null,
   val dataDeNascimento : String? = null,
   val anoNascimento: Int? = null,
   val mago: Boolean? = null,
   val ancestralidade: String? = null,
   val corDosOlhos: String? = null,
   val corDoCabelo: String? = null,
   val varinha: Wand? = null,
   val patrono: String? = null,
   val estudante:Boolean? = null,
   val  funcionario:Boolean? = null,
   val  ator: String? = null,
   val  atoresAlternativos: List<String>? = null,
   val  vivo: Boolean? = null,
   val imagem: String? = null

) {
   override fun toString(): String {
      return nome
   }

}