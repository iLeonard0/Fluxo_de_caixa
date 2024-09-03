package unipar.edu.utfpr.fluxo_de_caixa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LancamentoActivity : AppCompatActivity() {

    private lateinit var transacaoRepo: RepositoryDB
    private lateinit var adapter: TransacaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lancamento)

        transacaoRepo = RepositoryDB(this)
        val retorno = transacaoRepo.getAllTransacoes()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewLancamentos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TransacaoAdapter(retorno)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.updateTransacoes(transacaoRepo.getAllTransacoes())
    }
}
