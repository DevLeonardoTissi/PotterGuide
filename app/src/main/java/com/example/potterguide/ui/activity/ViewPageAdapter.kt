package com.example.potterguide.ui.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    var listaDeFragmentos: MutableList<Fragment> = ArrayList()
    var listaDeTitulos: MutableList<String> = ArrayList()

    fun adicionaFragmento(fragmento: Fragment, titulo: String) {
        listaDeFragmentos.add(fragmento)
        listaDeTitulos.add(titulo)
    }

    fun pegaTitulo(position: Int): String = listaDeTitulos[position]

    override fun getItemCount(): Int = listaDeFragmentos.size

    override fun createFragment(position: Int): Fragment = listaDeFragmentos[position]
}