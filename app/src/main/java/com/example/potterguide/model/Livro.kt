package com.example.potterguide.model

class Livro(
   val titulo: String,
   var subtitulo: String?,
   var  autores: List<String>?,
   var editora: String?,
   var dataDePublicacao: String?,
   var  descricao: String?,
   var  numeroDePaginas : Int?,
   var  categorias: List<String>,
   var  imageLinks: ImageLinks?,
   var   idioma: String?
) {
    override fun toString(): String {
        return imageLinks?.smallThumbnail.toString()
    }
}