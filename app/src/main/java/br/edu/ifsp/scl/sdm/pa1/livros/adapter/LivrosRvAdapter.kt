package br.edu.ifsp.scl.sdm.pa1.livros.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.pa1.livros.OnLivroClickListener
import br.edu.ifsp.scl.sdm.pa1.livros.R
import br.edu.ifsp.scl.sdm.pa1.livros.databinding.LayoutLivroBinding
import br.edu.ifsp.scl.sdm.pa1.livros.model.Livro

class LivrosRvAdapter(
    private val onLivroClickListener: OnLivroClickListener,
    private val livrosList: MutableList<Livro>
):RecyclerView.Adapter<LivrosRvAdapter.LivroLayoutHolder>() {

    inner class LivroLayoutHolder(layoutLivroBinding: LayoutLivroBinding): RecyclerView.ViewHolder(layoutLivroBinding.root), View.OnCreateContextMenuListener{
        val tituloTv: TextView = layoutLivroBinding.tvTitulo
        val primeiroAutorTv: TextView = layoutLivroBinding.tvAutor
        val editoraTv: TextView = layoutLivroBinding.tvEditora
        init{
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_main, menu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroLayoutHolder {
        val layoutLivroBinding = LayoutLivroBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LivroLayoutHolder(layoutLivroBinding)
    }

    override fun onBindViewHolder(holder: LivroLayoutHolder, position: Int) {
        val livro = livrosList[position]

        with(holder){
            editoraTv.text = livro.editora
            primeiroAutorTv.text = livro.primeiroAutor
            tituloTv.text = livro.titulo
        }

        holder.itemView.setOnClickListener{
            onLivroClickListener.onLivroClick(position)
        }
        holder.itemView.setOnLongClickListener{
            posicao = position
            false
        }
    }

    override fun getItemCount(): Int = livrosList.size

    var posicao: Int = -1

}