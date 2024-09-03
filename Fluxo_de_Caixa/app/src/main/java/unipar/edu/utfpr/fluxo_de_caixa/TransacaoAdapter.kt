package unipar.edu.utfpr.fluxo_de_caixa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

class TransacaoAdapter(private var transacoes: List<Transacao>) :
    RecyclerView.Adapter<TransacaoAdapter.TransacaoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransacaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transacao, parent, false)
        return TransacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransacaoViewHolder, position: Int) {
        val transacao = transacoes[position]
        holder.tipo.text = transacao.tipo
        holder.categoria.text = transacao.categoria
        holder.data.text = transacao.data
        holder.valor.text = "R$ ${String.format("%.2f", transacao.valor)}"

    }

    override fun getItemCount(): Int = transacoes.size

    fun updateTransacoes(newTransacoes: List<Transacao>) {
        transacoes = newTransacoes
        notifyDataSetChanged()
    }

    inner class TransacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tipo: MaterialTextView = itemView.findViewById(R.id.textTipo)
        val categoria: MaterialTextView = itemView.findViewById(R.id.textCategoria)
        val data: MaterialTextView = itemView.findViewById(R.id.textData)
        val valor: MaterialTextView = itemView.findViewById(R.id.textValor)

    }
}
