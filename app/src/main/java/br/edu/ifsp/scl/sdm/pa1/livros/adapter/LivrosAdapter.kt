package br.edu.ifsp.scl.sdm.pa1.livros.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.sdm.pa1.livros.R
import br.edu.ifsp.scl.sdm.pa1.livros.databinding.LayoutLivroBinding
import br.edu.ifsp.scl.sdm.pa1.livros.model.Livro

class LivrosAdapter(
    val contexto: Context,
    val livrosList: MutableList<Livro>
): ArrayAdapter<Livro>(contexto, R.layout.layout_livro, livrosList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val livroLayout: View

        if (convertView != null){
            livroLayout = convertView
        }
        else{
            // inflar view
            val layoutLivroBinding = LayoutLivroBinding.inflate((contexto.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater),parent,false)
            livroLayout = layoutLivroBinding.root

        }

        // preencher ou atualizar a view

        val livro: Livro = livrosList[position]

        livroLayout.findViewById<TextView>(R.id.tvTitulo).text = livro.titulo
        livroLayout.findViewById<TextView>(R.id.tvAutor).text = livro.primeiroAutor
        livroLayout.findViewById<TextView>(R.id.tvEditora).text = livro.editora

        return livroLayout
    }
}