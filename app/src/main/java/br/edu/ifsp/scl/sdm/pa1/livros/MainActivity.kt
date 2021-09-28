package br.edu.ifsp.scl.sdm.pa1.livros

import br.edu.ifsp.scl.sdm.pa1.livros.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter

import br.edu.ifsp.scl.sdm.pa1.livros.model.Livro

class MainActivity : AppCompatActivity() {

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data Source
    private val livrosList: MutableList<Livro> = mutableListOf()

    // Adapter
    private val livrosAdapter: ArrayAdapter<String> by lazy {

        // Cria o Adapter
        ArrayAdapter(
            this, android.R.layout.simple_list_item_1,
            livrosList.run {
                val livrosStringList = mutableListOf<String>()
                this.forEach { livro -> livrosStringList.add(livro.toString()) }
                livrosStringList
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        // Iniciar lista de Livros
        inicializarLivrosList()

        // Liga o Adapter ao ListView , auto iniciando o Adapter
        activityMainBinding.livrosLv.adapter = livrosAdapter
    }

    private fun inicializarLivrosList() {
        for (indice in 1..50) {
            livrosList.add(
                Livro(
                    "Título $indice",
                    "ISBN $indice",
                    "Autor: $indice",
                    "Editora: $indice",
                    indice,
                    indice
                )
            )
        }
    }
}