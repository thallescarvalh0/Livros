package br.edu.ifsp.scl.sdm.pa1.livros.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.edu.ifsp.scl.sdm.pa1.livros.R
import br.edu.ifsp.scl.sdm.pa1.livros.model.Livro

class LivrosAdapter(
    val contexto: Context,
    val livrosList: MutableList<Livro>
): ArrayAdapter<Livro>(contexto, R.layout.layout_livro, livrosList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }
}