package br.edu.ifsp.scl.sdm.pa1.livros

import android.content.Intent
import br.edu.ifsp.scl.sdm.pa1.livros.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.sdm.pa1.livros.adapter.LivrosAdapter
import br.edu.ifsp.scl.sdm.pa1.livros.adapter.LivrosRvAdapter

import br.edu.ifsp.scl.sdm.pa1.livros.model.Livro

class MainActivity : AppCompatActivity(), OnLivroClickListener {
    companion object Extras {
        const val EXTRA_LIVRO = "EXTRA_LIVRO"
        const val EXTRA_POSICAO = "EXTRA_POSICAO"
    }

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data Source
    private val livrosList: MutableList<Livro> = mutableListOf()

    // Adapter
    private val livrosAdapter: LivrosRvAdapter by lazy {
        LivrosRvAdapter(this,livrosList)
    }

    private val livrosLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.sairMI ->{
            finish()
            true
        }
        else ->{
            false
        }
    }

    private lateinit var livroActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarLivroActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        // Iniciar lista de Livros
        inicializarLivrosList()

        // associa view com adapter
        activityMainBinding.livrosRv.adapter = livrosAdapter
        activityMainBinding.livrosRv.layoutManager = livrosLayoutManager

        livroActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
            if (resultado.resultCode == RESULT_OK){
                resultado.data?.getParcelableExtra<Livro>(EXTRA_LIVRO)?.apply {
                    livrosList.add(this)
                    livrosAdapter.notifyDataSetChanged()
                }
            }
        }

        editarLivroActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
            if (resultado.resultCode == RESULT_OK){
                val posicao = resultado.data?.getIntExtra(EXTRA_POSICAO, -1)
                resultado.data?.getParcelableExtra<Livro>(EXTRA_LIVRO)?.apply {
                    if (posicao != null && posicao != -1){
                        livrosList[posicao] = this
                        livrosAdapter.notifyDataSetChanged()
                    }
                }
            }
        }


        activityMainBinding.adicionarLivroFab.setOnClickListener{
            livroActivityResultLauncher.launch(Intent(this, LivroActivity::class.java))
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val posicao = livrosAdapter.posicao

        return when(item.itemId){
            R.id.editarLivroMI ->{
                val livro = livrosList[posicao]
                val editarLivroIntent = Intent(this, LivroActivity::class.java)
                editarLivroIntent.putExtra(EXTRA_LIVRO, livro)
                editarLivroIntent.putExtra(EXTRA_POSICAO, posicao)
                editarLivroActivityResultLauncher.launch(editarLivroIntent)
                true
            }
            R.id.removerLivroMI ->{
                livrosList.removeAt(posicao)
                livrosAdapter.notifyDataSetChanged()
                true
            }
            else -> {
                false
            }
        }
    }


    private fun inicializarLivrosList() {
        for (indice in 1..5) {
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

    override fun onLivroClick(posicao: Int) {
        val livro = livrosList[posicao]
        val consultarLivroIntent = Intent(this, LivroActivity::class.java)
        consultarLivroIntent.putExtra(EXTRA_LIVRO, livro)
        startActivity(consultarLivroIntent)
    }
}