package com.example.potterguide.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivitySplashScreenBinding
import com.example.potterguide.extensions.mostraSnackBar
import com.example.potterguide.extensions.vaiPara
import com.example.potterguide.utils.checaConexao

class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoTentarNovamente()
        Handler(Looper.getMainLooper()).postDelayed({
            if (checaConexao(this)) {
                vaiPara(MainActivity::class.java)
                finish()
            } else {
                erroConecao()
            }
        }, 3000)
    }

    private fun configuraBotaoTentarNovamente() {
        val botaoRefresh = binding.botaoErroConexao
        botaoRefresh.setOnClickListener {
            if (checaConexao(this)) {
                vaiPara(MainActivity::class.java)
                finish()
            } else {
                mostraSnackBar(binding.root, getString(R.string.common_erro_conexao))
            }
        }
    }

    private fun erroConecao() {
        escondeItens()
        mensagemDeErro()
    }

    private fun escondeItens() {
        binding.apply {
            SplashScreenLogo.visibility = View.GONE
            textoDesenvolvedor.visibility = View.GONE
            gitHub.visibility = View.GONE
        }
    }

    private fun mensagemDeErro() {
        binding.SplashScreenImagemErroConexao.visibility = View.VISIBLE
        binding.textoErroConexao.visibility = View.VISIBLE
        binding.botaoErroConexao.visibility = View.VISIBLE
    }


}