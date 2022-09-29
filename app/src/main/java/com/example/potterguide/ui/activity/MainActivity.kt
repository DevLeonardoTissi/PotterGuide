package com.example.potterguide.ui.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.potterguide.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var texto: String? = null

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val cardViewTodosOsPersonagens = binding.CardViewBotaoTodosOsPersonagens
        cardViewTodosOsPersonagens.setOnClickListener {
            texto = "Todos os personagens"
            trocaTelaPersonagens(PersonagensActivity::class.java)
        }

        val cardViewAlunos = binding.CardViewBotaoAlunos
        cardViewAlunos.setOnClickListener {
            texto = "Alunos"
            trocaTelaPersonagens(PersonagensActivity::class.java)
        }

        val cardViewFuncionarios = binding.CardViewBotaoFuncionarios
        cardViewFuncionarios.setOnClickListener {
            texto = "Funcionários"
            trocaTelaPersonagens(PersonagensActivity::class.java)
        }

        val cardViewCasas = binding.CardViewBotaoCasas
        cardViewCasas.setOnClickListener {
            trocaTela(CasasActivity::class.java)
        }

        val cardViewCuriosidades = binding.CardViewBotaoCuriosidades
        cardViewCuriosidades.setOnClickListener {
            // trocaTela()
        }

        val cardViewLivros = binding.CardViewBotaoLivros
        cardViewLivros.setOnClickListener {
            // trocaTela()
        }

        val cardViewFilmes = binding.CardViewBotaoFilmes
        cardViewFilmes.setOnClickListener {
            // trocaTela()
        }

    }


    private fun trocaTelaPersonagens(destino: Class<*>) {
        val intent = Intent(this, destino).apply {
            putExtra(CHAVE_TELA, texto)
        }
        startActivity(intent)
    }

    private fun trocaTela(destino : Class<*>){
        val intent = Intent(this, destino)
        startActivity(intent)
    }


}