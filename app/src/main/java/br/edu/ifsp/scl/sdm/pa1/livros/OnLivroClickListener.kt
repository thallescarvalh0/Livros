package br.edu.ifsp.scl.sdm.pa1.livros

// interface que será implementada na Activity para tratar eventos de clique
//será usada no Adaptar para travar eventos de clique nas celulas do recycler
interface OnLivroClickListener {
    fun onLivroClick(posicao: Int)
}