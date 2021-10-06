package br.edu.ifsp.scl.sdm.pa1.livros

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.pa1.livros.databinding.ActivityLivroBinding
import android.os.Bundle
import br.edu.ifsp.scl.sdm.pa1.livros.model.Livro

class LivroActivity: AppCompatActivity() {

    private val livroActivityBinding: ActivityLivroBinding by lazy {
        ActivityLivroBinding.inflate(layoutInflater);
    }
    private lateinit var livro: Livro
    private var posicao = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(livroActivityBinding.root)


        livroActivityBinding.btnSalvar.setOnClickListener {
            val livro = Livro(
                livroActivityBinding.tituloEt.text.toString(),
                livroActivityBinding.isbnEt.text.toString(),
                livroActivityBinding.primeiroAutorEt.text.toString(),
                livroActivityBinding.editoraEt.text.toString(),
                Integer.parseInt(livroActivityBinding.edicaoEt.text.toString()),
                Integer.parseInt(livroActivityBinding.numPagEt.text.toString())
            )

            val resultadoIntent = Intent()
            resultadoIntent.putExtra(MainActivity.EXTRA_LIVRO, livro)
            if (posicao != -1){
                resultadoIntent.putExtra(MainActivity.EXTRA_POSICAO, posicao)
            }
            setResult(RESULT_OK, resultadoIntent)
            finish()
        }

        //verificando se é edição

        livro = intent.getParcelableExtra(MainActivity.EXTRA_LIVRO)!!
        posicao = intent.getIntExtra(MainActivity.EXTRA_POSICAO, -1)
        if (livro != null && posicao != -1){
            livroActivityBinding.tituloEt.setText(livro.titulo)
            livroActivityBinding.isbnEt.setText(livro.isbn)
            livroActivityBinding.primeiroAutorEt.setText(livro.primeiroAutor)
            livroActivityBinding.editoraEt.setText(livro.editora)
            livroActivityBinding.edicaoEt.setText(livro.edicao.toString())
            livroActivityBinding.numPagEt.setText(livro.paginas.toString())
        }

    }

}