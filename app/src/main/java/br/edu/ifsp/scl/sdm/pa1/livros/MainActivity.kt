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
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.sdm.pa1.livros.adapter.LivrosAdapter

import br.edu.ifsp.scl.sdm.pa1.livros.model.Livro

class MainActivity : AppCompatActivity() {
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
    private val livrosAdapter: LivrosAdapter by lazy {
        LivrosAdapter(this,livrosList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.adicionarLivroMI ->{
            livroActivityResultLauncher.launch(Intent(this, LivroActivity::class.java))
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

        // Liga o Adapter ao ListView , auto iniciando o Adapter
        activityMainBinding.livrosLv.adapter = livrosAdapter

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

        registerForContextMenu(activityMainBinding.livrosLv)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val posicao = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position

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
}