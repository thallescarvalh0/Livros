package br.edu.ifsp.scl.sdm.pa1.livros

import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.pa1.livros.databinding.ActivityLivroBinding
import android.os.Bundle

class LivroActivity: AppCompatActivity() {

    private val livroActivityBinding: ActivityLivroBinding by lazy {
        ActivityLivroBinding.inflate(layoutInflater);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(livroActivityBinding.root)
    }
}