package com.example.potterguide.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityDetalhesPersonagemBinding
import com.example.potterguide.extensions.tentaCarregarImagem
import com.example.potterguide.model.Personagem

class DetalhesPersonagemActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesPersonagemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buscaPersonagem()
    }

    private fun configuraToolbar(personagemNome: String) {
        val toolbar = binding.detalhesPersonagemActivityToolbar
        toolbar.apply {
            title = personagemNome
            setNavigationOnClickListener {
                finish()
            }
        }
    }


    private fun buscaPersonagem() {
        intent.getParcelableExtra<Personagem>(CHAVE_PERSONAGEM)?.let { personagemCarregado ->
            preencheCampos(personagemCarregado)
            configuraToolbar(personagemCarregado.nome)
        } ?: finish()
    }

    private fun preencheCampos(personagemCarregado: Personagem) {
        with(binding) {

            if (personagemCarregado.imagem.isNotEmpty()) {
                binding.personagemDetalheImagem.tentaCarregarImagem(personagemCarregado.imagem)
            } else {
                binding.personagemDetalheImagem.tentaCarregarImagem()
            }
            personagemDetalheNome.text = personagemCarregado.nome

            verificaSeVazio(
                personagemCarregado.especie,
                personagemDetalheTituloEspecie,
                personagemDetalheEspecie
            )
            verificaSeVazio(
                personagemCarregado.sexo,
                personagemDetalheTituloSexo,
                personagemDetalheSexo
            )
            verificaSeVazio(
                personagemCarregado.casa,
                personagemDetalheTituloCasa,
                personagemDetalheCasa
            )
            verificaSeVazio(
                personagemCarregado.dataDeNascimento,
                personagemDetalheTituloDataDeNascimento,
                personagemDetalheDataDeNascimento
            )
            verificaSeVazio(
                personagemCarregado.anoNascimento.toString(),
                personagemDetalheTituloAnoDeNascimento,
                personagemDetalheAnoDeNascimento
            )
            verificaSeVazio(
                personagemCarregado.mago.toString(),
                personagemDetalheTituloMago,
                personagemDetalheMago
            )
            verificaSeVazio(
                personagemCarregado.ancestralidade,
                personagemDetalheTituloAncestralidade,
                personagemDetalheAncestralidade
            )
            verificaSeVazio(
                personagemCarregado.corDosOlhos,
                personagemDetalheTituloCorDosOlhos,
                personagemDetalheCorDosOlhos
            )
            verificaSeVazio(
                personagemCarregado.corDoCabelo,
                personagemDetalheTituloCorDoCabelo,
                personagemDetalheCorDoCabelo
            )
            verificaSeVazio(
                personagemCarregado.patrono,
                personagemDetalheTituloPatrono,
                personagemDetalhePatrono
            )
            verificaSeVazio(
                personagemCarregado.estudante.toString(),
                personagemDetalheTituloEstudante,
                personagemDetalheEstudante
            )
            verificaSeVazio(
                personagemCarregado.funcionario.toString(),
                personagemDetalheTituloFuncionario,
                personagemDetalheFuncionario
            )
            verificaSeVazio(
                personagemCarregado.ator,
                personagemDetalheTituloAtor,
                personagemDetalheAtor
            )
            verificaSeVazio(
                personagemCarregado.vivo.toString(),
                personagemDetalheTituloVivo,
                personagemDetalheVivo
            )
        }
    }

    private fun verificaSeVazio(
        personagemcaracteristica: String? = null,
        textViewTitulo: TextView,
        textViewDetalhes: TextView
    ) {
        if (personagemcaracteristica?.isEmpty() == true) {
            textViewTitulo.visibility = View.GONE
            textViewDetalhes.visibility = View.GONE
        } else if (personagemcaracteristica == "0") {
            textViewTitulo.visibility = View.GONE
            textViewDetalhes.visibility = View.GONE
        } else if (personagemcaracteristica == getString(R.string.common_false)) {
            textViewDetalhes.text = getString(R.string.common_nao)
        } else if (personagemcaracteristica == getString(R.string.common_true)) {
            textViewDetalhes.text = getString(R.string.common_sim)
        } else {
            textViewDetalhes.text = personagemcaracteristica
        }
    }
}