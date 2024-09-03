package unipar.edu.utfpr.fluxo_de_caixa

data class Transacao(
    val tipo: String,
    val categoria: String,
    val valor: Double,
    val data: String
)
