package com.example.potterguide.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityMainBinding
import com.example.potterguide.ui.fragment.FeiticosFragment
import com.example.potterguide.ui.fragment.LivrosFragment
import com.example.potterguide.ui.fragment.PersonagensFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraTabLayout()
        configuraToolbar()
    }

    private fun configuraToolbar(){
        val toolbar = binding.mainActivityToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun configuraTabLayout() {
        binding
            .apply {
                val tabLayout = mainActivityTabLayout
                val viewPager = mainActivityViewPage
                val adapter = ViewPagerAdapter(this@MainActivity)
                viewPager.adapter = adapter
                adapter.adicionaFragmento(
                    PersonagensFragment(),
                    getString(R.string.activity_Main_ViewPage_Personagens)
                )
                adapter.adicionaFragmento(
                    FeiticosFragment(),
                    getString(R.string.activity_Main_ViewPage_Feiticos)
                )
                adapter.adicionaFragmento(
                    LivrosFragment(),
                    getString(R.string.activity_Main_ViewPage_Livros)
                )
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = adapter.pegaTitulo(position)
                }.attach()
            }
    }
}

